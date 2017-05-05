package uama.eagle.eye.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import uama.eagle.eye.util.JSONHelper;

/**
 * 作者：Administrator on 2016-03-01 20:45
 * 邮箱：597080641@qq.com
 */
public class EZCameraGroupInfo implements Serializable {
    ArrayList<EZCameraInfo> cameraInfos;//摄像头
    String groupName;//摄像头组名称
    String groupId;//摄像头组id
    String description;//描述

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCameraInfos(ArrayList<EZCameraInfo> cameraInfos) {
        this.cameraInfos = cameraInfos;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<EZCameraInfo> getCameraInfos() {
        return cameraInfos;
    }

    public static EZCameraGroupInfo buildBean(JSONObject o) throws JSONException {
        EZCameraGroupInfo info = new EZCameraGroupInfo();
        info.setGroupName(JSONHelper.getString(o, "groupName"));
        info.setGroupId(JSONHelper.getString(o, "groupId"));
        info.setDescription(JSONHelper.getString(o, "description"));
        ArrayList<EZCameraInfo> cameraInfos = new ArrayList<EZCameraInfo>();
        JSONArray jsonArray = o.getJSONArray("cameraInfos");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            EZCameraInfo cameraInfo = EZCameraInfo.buildBean(obj);
            EZCameraInfo ezCameraInfo = new EZCameraInfo();
            ezCameraInfo.setCameraId(cameraInfo.getCameraId());
            ezCameraInfo.setCameraName(cameraInfo.getCameraName());
            ezCameraInfo.setPicUrl(cameraInfo.getPicUrl());
//            ezCameraInfo.setOnlineStatus(Integer.parseInt(cameraInfo.getStatus()));
            cameraInfos.add(ezCameraInfo);
        }
        info.setCameraInfos(cameraInfos);
        return info;
    }

}
