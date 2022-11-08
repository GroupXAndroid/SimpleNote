package com.groupx.simplenote.common;

public class Const {

    public enum StatusPermission {
        CREATED, EDIT, VIEW
    }
    public static class PermissionDisplay {
        public static final String EDIT = "Edit";
        public static final String VIEW = "View";
    }

    public static class NoteDetailActivityMode {
        public static final short CREATE = 0;
        public static final short EDIT = 1;
        public static final short VIEW = 2;
    }

    public static class NoteStatus {
        public static final short NORMAL = 0;
        public static final short BIN = 1;
        public static final short FAVORITE = 2;
        public static final short ARCHIVE = 3;
    }

    public static class NoteRequestCode{
        public static final int REQUEST_CODE_SHOW = 0;
        public static final int REQUEST_CODE_CREATE = 1;
        public static final int REQUEST_CODE_UPDATE = 2;
        public static final int REQUEST_CODE_DELETE = 3;
    }
}
