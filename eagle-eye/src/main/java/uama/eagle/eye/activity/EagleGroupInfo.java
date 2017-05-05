package uama.eagle.eye.activity;

import java.util.List;

/**
 * Created by GuJiaJia on 2017/2/10.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 */

public class EagleGroupInfo extends BaseEntity{

    /**
     * data : {"eagleEyetoken":"at.br4b8aav63cchbqcdes0enus3kja57h3-6bdz6h31ob-0jxgyiu-1rclf1bjj","resultList":[{"groupName":"杭州万银国际","groupId":"28cd294d-056a-11e6-bb37-008cfae40fdc","description":"8","cameraInfos":[{"id":"5eb7367d-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"10cde6697ae842c28c10ee038e99da57","cameraNo":"1","cameraName":"二分杭州万银国际正门","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5eb84ab9-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"2a5db17aefc7432b92857c4d2734b043","cameraNo":"2","cameraName":"二分杭州万银国际后门","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5eb969b5-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"1fef39c07c364bb1a91fc642e860da6f","cameraNo":"3","cameraName":"二分杭州万银国际北通道","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5eba7bfc-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"94bea2c529b948c79a5e61a4e607b63e","cameraNo":"4","cameraName":"二分杭州万银国际客服前台","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5ebb7c7f-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"a3ee64063470429a942ad408ffb33284","cameraNo":"5","cameraName":"二分杭州万银国际大堂","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5ebc8334-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"491f03256481438fb5aa20bf96477e85","cameraNo":"6","cameraName":"二分杭州万银国际高配房","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5ebd8c9b-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"d1f00b0932224cc48549a299818f3a10","cameraNo":"7","cameraName":"二分杭州万银国际监控中心","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5ebecc5f-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"675dcaf054df42e99f07948b5b0f7416","cameraNo":"8","cameraName":"二分杭州万银国际服务中心","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""}]}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * eagleEyetoken : at.br4b8aav63cchbqcdes0enus3kja57h3-6bdz6h31ob-0jxgyiu-1rclf1bjj
         * resultList : [{"groupName":"杭州万银国际","groupId":"28cd294d-056a-11e6-bb37-008cfae40fdc","description":"8","cameraInfos":[{"id":"5eb7367d-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"10cde6697ae842c28c10ee038e99da57","cameraNo":"1","cameraName":"二分杭州万银国际正门","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5eb84ab9-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"2a5db17aefc7432b92857c4d2734b043","cameraNo":"2","cameraName":"二分杭州万银国际后门","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5eb969b5-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"1fef39c07c364bb1a91fc642e860da6f","cameraNo":"3","cameraName":"二分杭州万银国际北通道","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5eba7bfc-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"94bea2c529b948c79a5e61a4e607b63e","cameraNo":"4","cameraName":"二分杭州万银国际客服前台","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5ebb7c7f-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"a3ee64063470429a942ad408ffb33284","cameraNo":"5","cameraName":"二分杭州万银国际大堂","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5ebc8334-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"491f03256481438fb5aa20bf96477e85","cameraNo":"6","cameraName":"二分杭州万银国际高配房","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5ebd8c9b-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"d1f00b0932224cc48549a299818f3a10","cameraNo":"7","cameraName":"二分杭州万银国际监控中心","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""},{"id":"5ebecc5f-4a5b-11e6-bb37-008cfae40fdc","deviceId":"653316dd63734c2b993d6c8583e59749581295199","deviceSerial":"581295199","deviceName":"二分杭州万银国际","cameraId":"675dcaf054df42e99f07948b5b0f7416","cameraNo":"8","cameraName":"二分杭州万银国际服务中心","status":"1","display":null,"isShared":"2","picUrl":"","isEncrypt":"0","defence":"0","groupId":"","groupName":"","description":""}]}]
         */

        private String eagleEyetoken;
        private List<EZCameraGroupInfo> resultList;

        public int getIsEagleEye() {
            return isEagleEye;
        }

        public void setIsEagleEye(int isEagleEye) {
            this.isEagleEye = isEagleEye;
        }

        private int isEagleEye = -1;//0表示没有权限

        public String getEagleEyetoken() {
            return eagleEyetoken;
        }

        public void setEagleEyetoken(String eagleEyetoken) {
            this.eagleEyetoken = eagleEyetoken;
        }

        public List<EZCameraGroupInfo> getResultList() {
            return resultList;
        }

        public void setResultList(List<EZCameraGroupInfo> resultList) {
            this.resultList = resultList;
        }


    }
}
