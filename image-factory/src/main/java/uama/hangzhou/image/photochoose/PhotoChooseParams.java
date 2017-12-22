package uama.hangzhou.image.photochoose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuJiaJia on 2017/12/13.
 * E-mail 965939858@qq.com
 * Tel: 15050261230
 * 图片选择的属性配置类
 */

public class PhotoChooseParams implements Serializable{
    private int maxCounts = 9;//最大数量
    private List<String> selectedImages;//已选图片（带入）
    private int titleColor;//标题背景
    private int checkBoxBg;//选择按钮样式
    private int defaultCameraBg;//拍照的背景
    private int defaultCameraSrc;//拍照图标
    private boolean firstIsCamera = true;//第一张显示拍照图片
    private boolean showSkipText = true;//是否显示“跳过”
    private String maxTip;//达到上限的提示文案

    public int getMaxCounts() {
        return maxCounts;
    }

    public void setMaxCounts(int maxCounts) {
        this.maxCounts = maxCounts;
    }

    public List<String> getSelectedImages() {
        if(selectedImages == null){
            return new ArrayList<>();
        }
        return selectedImages;
    }

    public void setSelectedImages(List<String> selectedImages) {
        this.selectedImages = selectedImages;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getCheckBoxBg() {
        return checkBoxBg;
    }

    public void setCheckBoxBg(int checkBoxBg) {
        this.checkBoxBg = checkBoxBg;
    }

    public int getDefaultCameraBg() {
        return defaultCameraBg;
    }

    public void setDefaultCameraBg(int defaultCameraBg) {
        this.defaultCameraBg = defaultCameraBg;
    }

    public int getDefaultCameraSrc() {
        return defaultCameraSrc;
    }

    public void setDefaultCameraSrc(int defaultCameraSrc) {
        this.defaultCameraSrc = defaultCameraSrc;
    }

    public boolean isFirstIsCamera() {
        return firstIsCamera;
    }

    public void setFirstIsCamera(boolean firstIsCamera) {
        this.firstIsCamera = firstIsCamera;
    }

    public boolean isShowSkipText() {
        return showSkipText;
    }

    public void setShowSkipText(boolean showSkipText) {
        this.showSkipText = showSkipText;
    }

    public String getMaxTip() {
        return maxTip;
    }

    public void setMaxTip(String maxTip) {
        this.maxTip = maxTip;
    }
}
