package com.moovapp.riderapp.utils.retrofit.responseModels;

import java.util.List;

/**
 * Created by Lijo Mathew Theckanal on 19-Jul-18.
 */

public class SelectCollegeResponseModel {

    /**
     * status : true
     * data : {"details":[{"name":"sample school 1","id ":1},{"name":"sample school 2","id ":2},{"name":"sample school 3","id ":3},{"name":"sample school 4","id ":4}]}
     * message : College List
     * links : {"self":"http://themoovapp.com/api/v1/api/v1/auth/select_college","login":"http://themoovapp.com/api/v1/auth/login/email","forgot_password":"http://themoovapp.com/api/v1/auth/forgot","register":"http://themoovapp.com/api/v1/auth/register"}
     */

    private boolean status;
    private DataEntity data;
    private String message;

    @Override
    public String toString() {
        return "SelectCollegeResponseModel{" +
                "status=" + status +
                ", data=" + data.toString() +
                ", message='" + message + '\'' +
                ", links=" + links.toString() +
                '}';
    }

    private LinksEntity links;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LinksEntity getLinks() {
        return links;
    }

    public void setLinks(LinksEntity links) {
        this.links = links;
    }

    public static class DataEntity {
        private List<DetailsEntity> details;

        public List<DetailsEntity> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsEntity> details) {
            this.details = details;
        }

        public static class DetailsEntity {
            /**
             * name : sample school 1
             * id  : 1
             */

            private String name;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }

    public static class LinksEntity {
        /**
         * self : http://themoovapp.com/api/v1/api/v1/auth/select_college
         * login : http://themoovapp.com/api/v1/auth/login/email
         * forgot_password : http://themoovapp.com/api/v1/auth/forgot
         * register : http://themoovapp.com/api/v1/auth/register
         */

        private String self;
        private String login;
        private String forgot_password;
        private String register;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getForgot_password() {
            return forgot_password;
        }

        public void setForgot_password(String forgot_password) {
            this.forgot_password = forgot_password;
        }

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }
    }
}
