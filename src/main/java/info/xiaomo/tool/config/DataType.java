package info.xiaomo.tool.config;


import info.xiaomo.tool.util.FileGenerator;

/**
 * 属性类型
 *
 * @author 小莫
 * 2017-1-12 上午10:23:15
 */
public enum DataType {
    BYTE("byte") {
        @Override
        public String getPrimitiveType() {
            return "byte";
        }

        @Override
        public String getReferenceType() {
            return "Byte";
        }

        @Override
        public String write(DataConfig config, boolean isList) {
            if (isList) {
                return "this.writeByte(buf, this." + config.getName() + ".get(" + config.getName() + "_i));";
            } else {
                return "\t\tthis.writeByte(buf, " + config.getName() + ");";
            }
        }

        @Override
        public String read(DataConfig config, boolean isList) {
            if (isList) {
                //return "readByte(buf)";
                return "this." + config.getName() + ".add(readByte(buf));";
            }
            return "\t\tthis." + config.getName() + " = readByte(buf);";
        }

    },
    BOOLEAN("boolean") {// boolean

        @Override
        public String getPrimitiveType() {
            return "boolean";
        }

        @Override
        public String getReferenceType() {
            return "Boolean";
        }

        @Override
        public String write(DataConfig config, boolean isList) {
            if (isList) {
                return "this.writeBoolean(buf, this." + config.getName() + ".get(" + config.getName() + "_i));";
            }
            return "\t\tthis.writeBoolean(buf, " + config.getName() + ");";
        }

        @Override
        public String read(DataConfig config, boolean isList) {
            if (isList) {
                return "this." + config.getName() + ".add(readBoolean(buf));";
            }
            return "\t\tthis." + config.getName() + " = readBoolean(buf);";
        }
    },
    SHORT("short") {// short

        @Override
        public String getPrimitiveType() {
            return "short";
        }

        @Override
        public String getReferenceType() {
            return "Short";
        }

        @Override
        public String write(DataConfig config, boolean isList) {
            if (isList) {
                return "this.writeShort(buf, this." + config.getName() + ".get(" + config.getName() + "_i));";
            }
            return "\t\tthis.writeShort(buf, " + config.getName() + ");";
        }

        @Override
        public String read(DataConfig config, boolean isList) {
            if (isList) {
                return "this." + config.getName() + ".add(readShort(buf));";
            }
            return "\t\tthis." + config.getName() + " = readShort(buf);";
        }
    },
    INT("int") {
        @Override
        public String getPrimitiveType() {
            return "int";
        }

        @Override
        public String getReferenceType() {
            return "Integer";
        }

        @Override
        public String write(DataConfig config, boolean isList) {
            if (isList) {
                if (config.isOptimized()) {
                    if (config.isPositive()) {
                        return "this.writeInt(buf, this." + config.getName() + ".get(" + config.getName() + "_i), true);";
                    } else {
                        return "this.writeInt(buf, this." + config.getName() + ".get(" + config.getName() + "_i), false);";
                    }
                }
                return "this.writeInt(buf, this." + config.getName() + ".get(" + config.getName() + "_i));";
            }
            if (config.isOptimized()) {
                if (config.isPositive()) {
                    return "\t\tthis.writeInt(buf, " + config.getName() + ", true);";
                } else {
                    return "\t\tthis.writeInt(buf, " + config.getName() + ", false);";
                }
            }
            return "\t\tthis.writeInt(buf, " + config.getName() + ");";
        }

        @Override
        public String read(DataConfig config, boolean isList) {

            if (isList) {

                if (config.isOptimized()) {
                    if (config.isPositive()) {
                        return "this." + config.getName() + ".add(this.readInt(buf, true));";
                    } else {
                        return "this." + config.getName() + ".add(this.readInt(buf, false));";
                    }
                }
                return "this." + config.getName() + ".add(this.readInt(buf));";
            } else {
                if (config.isOptimized()) {
                    if (config.isPositive()) {
                        return "\t\tthis." + config.getName() + " = readInt(buf, true);";

                    } else {
                        return "\t\tthis." + config.getName() + " = readInt(buf, false);";
                    }
                }
                return "\t\tthis." + config.getName() + " = readInt(buf);";
            }
        }
    },
    LONG("long") {
        @Override
        public String getPrimitiveType() {
            return "long";
        }

        @Override
        public String getReferenceType() {
            return "Long";
        }

        @Override
        public String write(DataConfig config, boolean isList) {

            if (isList) {
                if (config.isOptimized()) {
                    if (config.isPositive()) {
                        return "this.writeLong(buf, this." + config.getName() + ".get(" + config.getName() + "_i), true);";
                    } else {
                        return "this.writeLong(buf, this." + config.getName() + ".get(" + config.getName() + "_i), false);";
                    }
                }
                return "this.writeLong(buf, this." + config.getName() + ".get(" + config.getName() + "_i));";
            } else if (config.isOptimized()) {
                if (config.isPositive()) {
                    return "\t\tthis.writeLong(buf, " + config.getName() + ", true);";
                } else {
                    return "\t\tthis.writeLong(buf, " + config.getName() + ", false);";
                }
            }
            return "\t\tthis.writeLong(buf, " + config.getName() + ");";
        }

        @Override
        public String read(DataConfig config, boolean isList) {
            if (isList) {
                if (config.isOptimized()) {
                    if (config.isPositive()) {
                        return "this." + config.getName() + ".add(this.readLong(buf, true));";
                    } else {
                        return "this." + config.getName() + ".add(this.readLong(buf, false));";
                    }
                }
                //return "this.readLong(buf)";
                return "this." + config.getName() + ".add(this.readLong(buf));";

            } else {
                if (config.isOptimized()) {
                    if (config.isPositive()) {
                        return "\t\tthis." + config.getName() + " = readLong(buf, true);";
                    } else {
                        return "\t\tthis." + config.getName() + " = readLong(buf, false);";
                    }
                }
                return "\t\tthis." + config.getName() + " = readLong(buf);";
            }
        }
    },
    STRING("string") {
        @Override
        public String getPrimitiveType() {
            return "String";
        }

        @Override
        public String getReferenceType() {
            return "String";
        }

        @Override
        public String write(DataConfig config, boolean isList) {
            if (isList) {
                return "this.writeString(buf, this." + config.getName() + ".get(" + config.getName() + "_i));";
            }
            return "\t\tthis.writeString(buf, " + config.getName() + ");";
        }

        @Override
        public String read(DataConfig config, boolean isList) {
            if (isList) {

                return "this." + config.getName() + ".add(this.readString(buf));";
            }
            return "\t\tthis." + config.getName() + " = readString(buf);";
        }
    },
    BEAN("bean") {
        @Override
        public String getPrimitiveType() {
            return null;
        }

        @Override
        public String getReferenceType() {
            return null;
        }

        @Override
        public String write(DataConfig config, boolean isList) {
            if (isList) {
                return "this.writeBean(buf, this." + config.getName() + ".get(" + config.getName() + "_i));";
            }
            return "\t\tthis.writeBean(buf, " + config.getName() + ");";
        }

        @Override
        public String read(DataConfig config, boolean isList) {
            String beanName = config.getBeanName();
            if (isList) {
                return
                        "if (readByte(buf) == 0) { \n" +
                                "\t\t\t\tthis." + config.getName() + ".add(null);\n" +
                                "\t\t\t} else {\n" +
                                "\t\t\t\t" + beanName + " " + FileGenerator.toFirstWorldLower(beanName) + " = new " + beanName + "();\n" +
                                "\t\t\t\t" + FileGenerator.toFirstWorldLower(beanName) + ".read(buf);\n" +
                                "\t\t\t\t" + "this." + config.getName() + ".add(" + FileGenerator.toFirstWorldLower(beanName) + ");\n" +
                                "\t\t\t}";

            }

            return "\t\tif (readByte(buf) != 0) {\n" +
                    "\t\t\t" + beanName + " " + FileGenerator.toFirstWorldLower(beanName) + " = new " + beanName + "();\n" +
                    "\t\t\t" + FileGenerator.toFirstWorldLower(beanName) + ".read(buf);\n" +
                    "\t\t\tthis." + config.getName() + " = " + FileGenerator.toFirstWorldLower(beanName) + ";\n" +
                    "\t\t}";

        }
    },;

    public abstract String getPrimitiveType();

    public abstract String getReferenceType();

    public abstract String write(DataConfig config, boolean isList);

    public abstract String read(DataConfig config, boolean isList);

    private String text;

    DataType(String text) {
        this.text = text;
    }

    public static DataType parse(String text) {
        for (DataType type : values()) {
            if (type.text.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return BEAN;
    }

}
