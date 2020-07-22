package com.frode.Concurrent.consistenthash;

import org.springframework.util.StringUtils;

import java.util.*;

public class HashConsistent {

    /**
     * 所有服务器ip信息
     */
    private ArrayList<String> ips = new ArrayList<>();

    /**
     * 虚拟节点和ip映射关系
     */
    private HashMap<Integer, String> virtualNodesIPRelationship = new HashMap();

    /**
     * 　所有虚拟节点
     */
    private ArrayList<Integer> virtualNodes = new ArrayList<>();

    /**
     * 　虚拟节点数量
     */
    private int VRITUALNUMS = 200;

    public HashConsistent() {
        ips.add("172.16.180.241");
        ips.add("172.16.180.242");
        ips.add("172.16.180.243");
        ips.add("172.16.180.244");
        ips.add("172.16.180.245");
        ips.add("172.16.180.246");
        ips.add("172.16.180.247");
        ips.add("172.16.180.248");
        ips.add("172.16.180.249");
        ips.add("172.16.180.240");
        initVirtualNodes();
    }

    public void setVRITUALNUMS(int VRITUALNUMS) {
        this.VRITUALNUMS = VRITUALNUMS;
    }

    /**
     * 批量增加服务器
     *
     * @param serveIp
     */
    public void addServeIp(ArrayList<String> serveIp) {
        if (StringUtils.isEmpty(ips)) {
            ips = new ArrayList<>();
        }
        this.ips.addAll(serveIp);
        this.initVirtualNodes();
    }

    /**
     * 增加服务器
     *
     * @param serveIp
     */
    public void addServeIp(String serveIp) {
        if (StringUtils.isEmpty(ips)) {
            ips = new ArrayList<>();
        }
        this.ips.add(serveIp);
        this.initVirtualNodes();
    }

    /**
     * 根据ip来创建虚拟节点
     */
    public void initVirtualNodes() {

        for (String ip : ips) {
            for (int i = 0; i < VRITUALNUMS; i++) {
                String vritualIp = ip + "#" + i;
                int virtualIpHash = getHash(vritualIp);
                virtualNodesIPRelationship.put(virtualIpHash, ip);
                virtualNodes.add(virtualIpHash);
            }
        }

        sortList(virtualNodes);

    }

    /**
     * 对虚拟节点进行排序
     *
     * @param list
     */
    private void sortList(List list) {
        Collections.sort(list);
    }

    public String getRealIp(String str) {
        int strHash = getHash(str);

        int rightRecentlyVirtual = getRightRecentlyVirtual(strHash);
        String ip = virtualNodesIPRelationship.get(rightRecentlyVirtual);

        return ip;
    }


    /**
     * 获取顺时针最近的虚拟节点
     *
     * @param hashString
     * @return
     */
    private int getRightRecentlyVirtual(int hashString) {
        int position = 0;
        for (int i = 0; i < virtualNodes.size(); i++) {
            int foo = virtualNodes.get(i);
            if (foo > hashString) {
                position = i;
                break;
            }
        }
        return virtualNodes.get(position);

    }

    /**
     * 获取hashcode
     *
     * @param str
     * @return
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    /**
     * 求标准差
     *
     * @param x
     * @return
     */
    public static double StandardDiviation(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        double dAve = sum / m;//求平均值
        double dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return Math.sqrt(dVar / m);
    }

    public static void main(String[] args) {

        double[] ipsTotal = new double[10];

        HashConsistent hashConsistent = new HashConsistent();

        for (int i = 0; i < 1000000; i++) {
            String realIp = hashConsistent.getRealIp("A12738749837482749" + i);
            if ("172.16.180.241".equals(realIp)) {
                ipsTotal[0]++;
            } else if ("172.16.180.242".equals(realIp)) {
                ipsTotal[1]++;
            } else if ("172.16.180.243".equals(realIp)) {
                ipsTotal[2]++;
            } else if ("172.16.180.244".equals(realIp)) {
                ipsTotal[3]++;
            } else if ("172.16.180.245".equals(realIp)) {
                ipsTotal[4]++;
            } else if ("172.16.180.246".equals(realIp)) {
                ipsTotal[5]++;
            } else if ("172.16.180.247".equals(realIp)) {
                ipsTotal[6]++;
            } else if ("172.16.180.248".equals(realIp)) {
                ipsTotal[7]++;
            } else if ("172.16.180.249".equals(realIp)) {
                ipsTotal[8]++;
            } else if ("172.16.180.240".equals(realIp)) {
                ipsTotal[9]++;
            }
        }
        System.out.println("172.16.180.241:" + ipsTotal[0] + "次");
        System.out.println("172.16.180.242:" + ipsTotal[1] + "次");
        System.out.println("172.16.180.243:" + ipsTotal[2] + "次");
        System.out.println("172.16.180.244:" + ipsTotal[3] + "次");
        System.out.println("172.16.180.245:" + ipsTotal[4] + "次");
        System.out.println("172.16.180.246:" + ipsTotal[5] + "次");
        System.out.println("172.16.180.247:" + ipsTotal[6] + "次");
        System.out.println("172.16.180.248:" + ipsTotal[7] + "次");
        System.out.println("172.16.180.249:" + ipsTotal[8] + "次");
        System.out.println("172.16.180.240:" + ipsTotal[9] + "次");
        System.out.println("标准差为：" + StandardDiviation(ipsTotal));

    }


}
