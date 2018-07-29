package EugeneAlgorithm.entity;

import java.util.Random;

public class Neural {
    private double weight;//所连边的加权和，如果为输入层的神经元的话，那么weight就为输入的值
    private double[] edgeWeight;

    public Neural(double weight, int size) {//size为每个神经元连接的边的数量
        Random random = new Random();
        this.weight = weight;
        edgeWeight = new double[size];
        for (int i = 0; i < size; i++) {
            edgeWeight[i] = random.nextDouble() * 2 - 1;//边的权重通过随机数初始化为-1--1
        }
    }

    public void setEdgeWeight(double[] edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

    public double[] getEdgeWeight() {
        return edgeWeight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
