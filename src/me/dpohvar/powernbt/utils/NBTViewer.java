package me.dpohvar.powernbt.utils;

import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.nbt.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: DPOH-VAR
 * Date: 07.06.13
 * Time: 1:11
 */
public class NBTViewer {

    static int v_limit = 60;
    static int h_limit = 10;

    public static String getShortValueWithPrefix(NBTBase base, boolean hex){
        NBTType type = base.getType();
        return String.valueOf(type.color) + type.name + ':' + ' ' + ChatColor.RESET + getShortValue(base, hex);
    }
    public static String getShortValue(NBTBase base, boolean hex){
        String value = "";
        switch (base.getTypeId()){
            case 1:{ // byte
                byte v = ((NBTTagByte) base).get();
                if (hex) value = "#"+Integer.toHexString(v&0xFF);
                else value = String.valueOf(v);
                break;
            }
            case 2:{ // short
                short v = ((NBTTagShort) base).get();
                if (hex) value = "#"+Integer.toHexString(v&0xFFFF);
                else value = String.valueOf(v);
                break;
            }
            case 3:{ // int
                int v = ((NBTTagInt) base).get();
                if (hex) value = "#"+Long.toHexString(v&0xFFFFFFFFL);
                else value = String.valueOf(v);
                break;
            }
            case 4:{ // long
                long v = ((NBTTagLong) base).get();
                if (hex) value = "#"+Long.toHexString(v);
                else value = String.valueOf(v);
                break;
            }
            case 5:{ // float
                float v = ((NBTTagFloat) base).get();
                if (hex) value = "#"+Float.toHexString(v);
                else value = String.valueOf(v);
                break;
            }
            case 6:{ // double
                double v = ((NBTTagDouble) base).get();
                if (hex) value = "#"+Double.toHexString(v);
                else value = String.valueOf(v);
                break;
            }
            case 7:{ // bytearray
                NBTTagByteArray v = ((NBTTagByteArray) base);
                if (v.size()==0) value = PowerNBT.plugin.translate("data_emptyarray");
                else {
                    value=v.size()+": [";
                    if (hex) {
                        ArrayList<String> h = new ArrayList<String>(v.size());
                        for(byte b:v.get()) h.add(Integer.toHexString(b&0xFF));
                        value += "#"+StringUtils.join(h,',');
                    } else{
                        value += StringUtils.join(v.iterator(),',');
                    }
                    value+="]";
                }
                break;
            }
            case 8:{ //string
                value = ((NBTTagString)base).get();
                if (hex) {
                    try{
                        ArrayList<String> h = new ArrayList<String>();
                        for(byte b:value.getBytes("UTF8")) h.add(Integer.toHexString(b&0xFF));
                        value = StringUtils.join(h,' ');
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case 9:{ // list
                NBTTagList list = ((NBTTagList)base);
                NBTType listType = NBTType.fromByte(list.getSubTypeId());
                if (list.size()==0){
                    value = PowerNBT.plugin.translate("data_emptylist");
                } else {
                    value = PowerNBT.plugin.translate("data_elements",list.size())
                            + " " + listType.color + listType.name + ChatColor.RESET;
                }
                break;
            }
            case 10:{ // compound
                NBTTagCompound tag = ((NBTTagCompound)base);
                if (tag.size()==0){
                    value = PowerNBT.plugin.translate("data_emptycompound");
                } else {
                    ArrayList<String> h = new ArrayList<String>();
                    for(Map.Entry<String, NBTBase> b: tag.entrySet()){
                        h.add(b.getValue().getType().color+b.getKey());
                    }
                    value=tag.size()+": "+StringUtils.join(h,ChatColor.RESET+",");
                }
                break;
            }
            case 11:{ // intarray
                NBTTagIntArray v = ((NBTTagIntArray) base);
                if(v.size()==0) value = PowerNBT.plugin.translate("data_emptyarray");
                else {
                    value=v.size()+": [";
                    if (hex) {
                        ArrayList<String> h = new ArrayList<String>(v.size());
                        for(int b:v.get()) h.add(Long.toHexString(b&0xFFFFFFFFL));
                        value += "#"+StringUtils.join(h,',');
                    } else{
                        value += StringUtils.join(v.iterator(),',');
                    }
                    value+="]";
                }
                break;
            }
        }
        int overtext = Math.max(ChatColor.stripColor(value).length()-v_limit,value.length()-v_limit*2);
        String reretPattern = ChatColor.RESET.toString();
        if ( value.endsWith(reretPattern) ) {
            value = value.substring(0,value.length()-reretPattern.length());
        }
        if (overtext>0){
            value=value.substring(0,v_limit-1).concat("\u2026");
        }
        return value;
    }

    public static String getFullValue(NBTBase base,int start, int end, boolean hex){
        //if(start==0 && end==0) end = v_limit;
        if(base==null) return PowerNBT.plugin.translate("error_null");
        if(start>end) { int t=start; start=end; end=t; }
        String name = base.getName();
        NBTType type = base.getType();
        ChatColor color = type.color;
        String prefix;
        String value = "";
        switch (base.getTypeId()){
            case 1:{ // byte
                byte v = ((NBTTagByte) base).get();
                if (hex) value = "#"+Integer.toHexString(v&0xFF);
                else value = String.valueOf(v);
                break;
            }
            case 2:{ // short
                short v = ((NBTTagShort) base).get();
                if (hex) value = "#"+Integer.toHexString(v&0xFFFF);
                else value = String.valueOf(v);
                break;
            }
            case 3:{ // int
                int v = ((NBTTagInt) base).get();
                if (hex) value = "#"+Long.toHexString(v&0xFFFFFFFFL);
                else value = String.valueOf(v);
                break;
            }
            case 4:{ // long
                long v = ((NBTTagLong) base).get();
                if (hex) value = "#"+Long.toHexString(v);
                else value = String.valueOf(v);
                break;
            }
            case 5:{ // float
                float v = ((NBTTagFloat) base).get();
                if (hex) value = "#"+Float.toHexString(v);
                else value = String.valueOf(v);
                break;
            }
            case 6:{ // double
                double v = ((NBTTagDouble) base).get();
                if (hex) value = "#"+Double.toHexString(v);
                else value = String.valueOf(v);
                break;
            }
            case 7:{ // bytearray
                if(start==0 && end==0) end = h_limit;
                NBTTagByteArray v = ((NBTTagByteArray) base);
                if (v.size()==0) {
                    value = PowerNBT.plugin.translate("data_emptyarray");
                    break;
                } else if (start>v.size()) {
                    value = "\n"+PowerNBT.plugin.translate("data_outofrange");
                    break;
                } else {
                    StringBuilder buffer = new StringBuilder();
                    for(int i=start; i<end; i++){
                        if(i>=v.size()) break;
                        buffer.append("\n").append(type.color).append("[").append(i).append("] ").append(ChatColor.RESET);
                        if (hex) buffer.append("#").append(Integer.toHexString(v.get(i)&0xFF));
                        else buffer.append(v.get(i) & 0xFF);
                    }
                    value = PowerNBT.plugin.translate("data_elements",v.size()) + buffer.toString();
                }
                break;
            }
            case 8:{ //string
                boolean postfix = false;//
                if(start==0 && end==0) {
                    end = v_limit*h_limit;
                    postfix = true;
                }
                value = ((NBTTagString)base).get();
                if (start>value.length()) {
                    value = PowerNBT.plugin.translate("data_outofrange");
                    break;
                } else {
                    if (end>value.length()) {
                        end=value.length();
                        postfix = false;
                    }
                    value = value.substring(start,end);
                }
                if (hex) {
                    try{
                        ArrayList<String> h = new ArrayList<String>();
                        for(byte b:value.getBytes("UTF8")) h.add(Integer.toHexString(b&0xFF));
                        value = StringUtils.join(h,' ');
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if (postfix) value += '\u2026';
                break;
            }
            case 9:{ // list
                if(start==0 && end==0) end = h_limit;
                NBTType listType = NBTType.fromByte(((NBTTagList)base).getSubTypeId());
                NBTTagList list = ((NBTTagList)base);
                if (list.size()==0){
                    value = PowerNBT.plugin.translate("data_emptylist");
                    break;
                }
                StringBuilder buffer = new StringBuilder();
                for(int i=start; i<end; i++){
                    if(i>=list.size()) break;
                    NBTBase b = list.get(i);
                    buffer  .append('\n')
                            .append(listType.color)
                            .append(ChatColor.BOLD)
                            .append("[")
                            .append(i)
                            .append("] ")
                            .append(ChatColor.RESET)
                            .append(getShortValue(b,hex));
                }
                value = PowerNBT.plugin.translate("data_elements",list.size())
                        + " " + listType.color + listType.name
                        + buffer.toString();
                break;
            }
            case 10:{ // compound
                if(start==0 && end==0) end = h_limit;
                List<Map.Entry<String,NBTBase>> list = new ArrayList<Map.Entry<String,NBTBase>>(((NBTTagCompound)base).entrySet());
                if (list.size()==0){
                    value = PowerNBT.plugin.translate("data_emptycompound");
                    break;
                }
                StringBuilder buffer = new StringBuilder();
                for(int i=start; i<end; i++){
                    if(i>=list.size()) break;
                    NBTBase b = list.get(i).getValue();
                    String currentName = list.get(i).getKey();
                    NBTType t = b.getType();
                    ChatColor c;
                    if (b instanceof NBTTagList) c=NBTType.fromByte(((NBTTagList)b).getSubTypeId()).color;
                    else c = t.color;
                    String bolder = "";
                    switch (t){
                        case LIST:
                        case COMPOUND:
                        bolder = ChatColor.BOLD.toString();
                    }
                    buffer  .append('\n')
                            .append(c)
                            .append(t.prefix)
                            .append(' ')
                            .append(bolder)
                            .append(currentName)
                            .append(':')
                            .append(ChatColor.RESET)
                            .append(' ')
                            .append(getShortValue(b,hex));
                }
                value = PowerNBT.plugin.translate("data_elements",list.size()) + buffer.toString();
                break;
            }
            case 11:{ // intarray
                if(start==0 && end==0) end = h_limit;
                NBTTagIntArray v = ((NBTTagIntArray) base);
                if (v.size()==0) {
                    value = PowerNBT.plugin.translate("data_emptyarray");
                    break;
                } else if (start>v.size()) {
                    value = "\n"+PowerNBT.plugin.translate("data_outofrange");
                    break;
                } else {
                    StringBuilder buffer = new StringBuilder();
                    for(int i=start; i<end; i++){
                        if(i>=v.size()) break;
                        buffer.append("\n").append(type.color).append("[").append(i).append("] ").append(ChatColor.RESET);
                        if (hex) buffer.append("#").append(Long.toHexString(v.get(i)&0xFFFFFFFFL));
                        else buffer.append(v.get(i));
                    }
                    value = PowerNBT.plugin.translate("data_elements",v.size()) + buffer.toString();
                }
                break;
            }
        }
        if(name.isEmpty()) prefix = color + type.name + ChatColor.RESET + ":";
        else prefix = color + type.name + " " + ChatColor.BOLD + name + ChatColor.RESET + ":";
        return prefix+" "+value;
    }
}
