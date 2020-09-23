package com.yzq.mvvm.bean;

import java.io.Serializable;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by jingbin on 2016/11/24.
 */

public class GankIoDataBean extends BaseObservable implements Serializable {

    private int status;
    /**
     * _id : 5832662b421aa929b0f34e99
     * createdAt : 2016-11-21T11:12:43.567Z
     * desc :  深入Android渲染机制
     * publishedAt : 2016-11-24T11:40:53.615Z
     * source : web
     * type : Android
     * url : http://blog.csdn.net/ccj659/article/details/53219288
     * used : true
     * who : Chauncey
     */

    private List<ResultBean> data;

    public static class ResultBean extends BaseObservable implements Serializable {


        private String createdAt;

        private String desc;

        private String publishedAt;
        private String source;

        private String type;

        private String category;

        private String url;

        private boolean used;

        private String author;

        private List<String> images;

        private String image;

        private String title;

        @Bindable
        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Bindable
        public String getCreatedAt() {
            return createdAt;
        }

        @Bindable
        public String getDesc() {
            return desc;
        }

        @Bindable
        public String getPublishedAt() {
            return publishedAt;
        }

        @Bindable
        public String getSource() {
            return source;
        }

        @Bindable
        public String getType() {
            return type;
        }

        @Bindable
        public String getUrl() {
            return url;
        }

        @Bindable
        public String getAuthor() {
            return author;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;

        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;

        }

        public void setSource(String source) {
            this.source = source;

        }

        public void setType(String type) {
            this.type = type;

        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setAuthor(String author) {
            this.author = author;

        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        @Bindable
        public List<String> getImages() {
            return images;
        }
    }

    public int getStatus() {
        return status;
    }

    @Bindable
    public List<ResultBean> getResults() {
        return data;
    }

    public void setResults(List<ResultBean> results) {
        this.data = results;
    }
}
