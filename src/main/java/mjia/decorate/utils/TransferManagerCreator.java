package mjia.decorate.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

public class TransferManagerCreator {
    // 创建 TransferManager 实例，这个实例用来后续调用高级接口
    private static TransferManager createTransferManager(String secretId, String secretKey) {
        try {
            // 创建一个 COSClient 实例，这是访问 COS 服务的基础实例。
            // 详细代码参见本页: 简单操作 -> 创建 COSClient
            COSClient cosClient = createCOSClient(secretId, secretKey);

            // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
            // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
            ExecutorService threadPool = new ThreadPoolExecutor(5, 5,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());

            // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
            TransferManager transferManager = new TransferManager(cosClient, threadPool);

            // 设置高级接口的配置项
            // 分块上传阈值和分块大小分别为 5MB 和 1MB
            TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
            transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
            transferManagerConfiguration.setMinimumUploadPartSize(1 * 1024 * 1024);
            //transferManager.setConfiguration(transferManagerConfiguration);

            return transferManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void shutdownTransferManager(TransferManager transferManager) {
        // 指定参数为 true, 则同时会关闭 transferManager 内部的 COSClient 实例。
        // 指定参数为 false, 则不会关闭 transferManager 内部的 COSClient 实例。
        try {
            if (Objects.nonNull(transferManager)) {
                transferManager.shutdownNow(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 上传对象
    public static Upload upload(String secretId, String secretKey, String localFilePath, String fineName) {
        // 使用高级接口必须先保证本进程存在一个 TransferManager 实例，如果没有则创建
        // 详细代码参见本页：高级接口 -> 创建 TransferManager
        TransferManager transferManager = createTransferManager(secretId, secretKey);

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
                String bucketName = "tupian-1314348862";
        // 对象键(Key)是对象在存储桶中的唯一标识。
                String key = fineName;
        // 本地文件路径
                File localFile = new File(localFilePath + fineName);
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);

        // 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
        // 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
                putObjectRequest.setStorageClass(StorageClass.Standard);

        //若需要设置对象的自定义 Headers 可参照下列代码,若不需要可省略下面这几行,对象自定义 Headers 的详细信息可参考https://cloud.tencent.com/document/product/436/13361
                //ObjectMetadata objectMetadata = new ObjectMetadata();

        //若设置Content-Type、Cache-Control、Content-Disposition、Content-Encoding、Expires这五个字自定义 Headers，推荐采用objectMetadata.setHeader()
                //objectMetadata.setHeader(key, value);
        //若要设置 “x-cos-meta-[自定义后缀]” 这样的自定义 Header，推荐采用
        /*    Map<String, String> userMeta = new HashMap<String, String>();
                userMeta.put("x-cos-meta-decorate", "value");
                objectMetadata.setUserMetadata(userMeta);*/

                //putObjectRequest.withMetadata(objectMetadata);

                try {
                    // 高级接口会返回一个异步结果Upload
                    // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
                    Upload upload = transferManager.upload(putObjectRequest);
                    UploadResult uploadResult = upload.waitForUploadResult();
                    return upload;
                } catch (CosServiceException e) {
                    e.printStackTrace();
                } catch (CosClientException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        // 确定本进程不再使用 transferManager 实例之后，关闭之
        // 详细代码参见本页：高级接口 -> 关闭 TransferManager
                shutdownTransferManager(transferManager);
                return null;
    }

    private static COSClient createCOSClient(String secretId, String secretKey) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
                Region region = new Region("ap-chengdu");
                ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
                clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }
}
