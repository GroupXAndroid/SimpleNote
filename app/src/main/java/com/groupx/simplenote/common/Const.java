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
}
