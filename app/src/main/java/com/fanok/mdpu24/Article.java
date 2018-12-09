package com.fanok.mdpu24;

public class Article {
        private String image;
        private String urlArticle;
        private String title;
        private String date;

        public Article(String image, String urlArticle, String title, String date) {
            this.image = image;
            this.urlArticle = urlArticle;
            this.title = title;
            this.date = date;
        }

        Article() {
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        String getUrlArticle() {
            return urlArticle;
        }

        void setUrlArticle(String urlArticle) {
            this.urlArticle = urlArticle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
}
