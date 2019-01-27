package com.fanok.mdpu24;

public class TypeTimeTable {
    public static final int studentTimeTable = 0;
    public static final int teacherTimeTable = 1;
    public static final int curatorTimeTable = 2;
    public static final int starostaTimeTable = 3;

    private static String group = "";
    private static int type = 0;

    public static String getGroup() {
        return group;
    }

    public static void setGroup(String group) {
        TypeTimeTable.group = group;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        TypeTimeTable.type = type;
    }
}
