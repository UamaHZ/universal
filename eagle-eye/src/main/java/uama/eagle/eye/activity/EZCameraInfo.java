package uama.eagle.eye.activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import uama.eagle.eye.util.JSONHelper;

/**
 * 作者：Administrator on 2016-03-04 14:26
 * 邮箱：597080641@qq.com
 */
public class EZCameraInfo implements Serializable {
    String groupName;
    String picUrl;
    String cameraNo;
    String status;
    String isEncrypt;
    String display;
    String cameraId;
    String id;
    String groupId;
    String defence;
    String cameraName;
    String deviceName;
    String description;
    String isShared;
    String deviceId;
    String deviceSerial;
    int type;
    String monitorUrl;

    public static EZCameraInfo buildBean(JSONObject o) throws JSONException {
        EZCameraInfo tb = new EZCameraInfo();
        tb.setGroupName(JSONHelper.getString(o, "groupName"));
        tb.setPicUrl(JSONHelper.getString(o, "picUrl"));
        tb.setCameraNo(JSONHelper.getString(o, "cameraNo"));
        tb.setStatus(JSONHelper.getString(o, "status"));
        tb.setIsEncrypt(JSONHelper.getString(o, "isEncrypt"));
        tb.setDisplay(JSONHelper.getString(o, "display"));
        tb.setCameraId(JSONHelper.getString(o, "cameraId"));

        tb.setId(JSONHelper.getString(o, "id"));
        tb.setGroupId(JSONHelper.getString(o, "groupId"));
        tb.setDefence(JSONHelper.getString(o, "defence"));
        tb.setCameraName(JSONHelper.getString(o, "cameraName"));
        tb.setDeviceName(JSONHelper.getString(o, "deviceName"));
        tb.setDescription(JSONHelper.getString(o, "description"));

        tb.setIsShared(JSONHelper.getString(o, "isShared"));
        tb.setDeviceId(JSONHelper.getString(o, "deviceId"));
        tb.setDeviceSerial(JSONHelper.getString(o, "deviceSerial"));
        tb.setType(JSONHelper.getInt(o,"type"));
        tb.setMonitorUrl(JSONHelper.getString(o,"monitorUrl"));
        return tb;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCameraNo() {
        return cameraNo;
    }

    public void setCameraNo(String cameraNo) {
        this.cameraNo = cameraNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(String isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDefence() {
        return defence;
    }

    public void setDefence(String defence) {
        this.defence = defence;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsShared() {
        return isShared;
    }

    public void setIsShared(String isShared) {
        this.isShared = isShared;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }
}
