package EugeneAlgorithm;

import EugeneAlgorithm.entity.Neural;

import java.util.ArrayList;
import java.util.Random;

public class BPNeuralNetwork {

    private static ArrayList<Neural> inputLayerNeurals = new ArrayList<>();
    private static ArrayList<Neural> invisibleLayerNeurals = new ArrayList<>();
    private static ArrayList<Neural> outputLayerNeurals = new ArrayList<>();

    /**
     * @param size the number of input layer neurals
     */
    public static void init(int size) {// 1 1 1 1 1 0 0    ->  -1--1    weight is useless
        double[] inputs = new double[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            inputs[i] = random.nextDouble() * 2 - 1;//边的权重通过随机数初始化为-1--1
        }

        int invisibleLayerNumber = (inputs.length + 1) / 2;
        for (int i = 0; i < inputs.length; i++) {
            inputLayerNeurals.add(new Neural(inputs[i], 1));
        }

        for (int i = 0; i < invisibleLayerNumber; i++) {
            Neural neural = new Neural(0, inputs.length);
            double weight = 0;
            for (int j = 0; j < inputs.length; j++) {
                weight += inputs[j] * neural.getEdgeWeight()[j];
            }
            neural.setWeight(weight);
            invisibleLayerNeurals.add(neural);
        }

        Neural outputNeural = new Neural(0, invisibleLayerNumber);
        double weight = 0;
        for (int i = 0; i < invisibleLayerNumber; i++) {
            weight += invisibleLayerNeurals.get(i).getWeight() * outputNeural.getEdgeWeight()[i];
        }
        outputNeural.setWeight(weight);
        outputLayerNeurals.add(outputNeural);
    }

    public static void printAllNeurals() {
        System.out.println("InputLayer");
        for (Neural neural : inputLayerNeurals) {
            System.out.println(neural.getWeight());
        }

        System.out.println("InvisibleLayer");
        for (Neural neural : invisibleLayerNeurals) {
            System.out.println(neural.getWeight());
        }

        System.out.println("OutputLayer");
        for (Neural neural : outputLayerNeurals) {
            System.out.println(neural.getWeight());
        }
    }

    public static void printAllEdges() {
        System.out.println("InputLayer->InvisibleLayer");
        for (int i = 0; i < invisibleLayerNeurals.size(); i++) {
            System.out.println("Number." + i + "'s edges");
            for (int j = 0; j < invisibleLayerNeurals.get(i).getEdgeWeight().length; j++) {
                System.out.println(invisibleLayerNeurals.get(i).getEdgeWeight()[j]);
            }
        }

        System.out.println("InvisibleLayer->OutputLayer");
        for (int j = 0; j < outputLayerNeurals.get(0).getEdgeWeight().length; j++) {
            System.out.println(outputLayerNeurals.get(0).getEdgeWeight()[j]);
        }
    }

    public static void train(int times, double sources[], double result) {
        for (int i = 0; i < times; i++) {
            train(sources, result);

        }
    }

    public static void train(double sources[], double result) {//difference↑ feedback↑
        double weight = calculateWeight(sources);
        double difference = weight - result;
        if (Math.abs(difference) > 0.5 && Math.abs(difference) < 1) {
            for (Neural neural : outputLayerNeurals) {
                double[] edgeWeights = neural.getEdgeWeight();
                for (int i = 0; i < invisibleLayerNeurals.size(); i++) {
                    if ((invisibleLayerNeurals.get(i).getWeight() > 0 && result > 0) || (invisibleLayerNeurals.get(i).getWeight() < 0 && result < 0)) {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] += 0.0005 * invisibleLayerNeurals.get(i).getWeight();
                    } else {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] -= 0.0005 * invisibleLayerNeurals.get(i).getWeight();
                    }
                }
                neural.setEdgeWeight(edgeWeights);
            }
            for (Neural neural : invisibleLayerNeurals) {
                double[] edgeWeights = neural.getEdgeWeight();
                for (int i = 0; i < inputLayerNeurals.size(); i++) {
                    if ((inputLayerNeurals.get(i).getWeight() > 0 && result > 0) || (inputLayerNeurals.get(i).getWeight() < 0 && result < 0)) {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] += 0.0005 * inputLayerNeurals.get(i).getWeight();
                    } else {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] -= 0.0005 * inputLayerNeurals.get(i).getWeight();
                    }
                }
                neural.setEdgeWeight(edgeWeights);
            }
        } else if (Math.abs(difference) >= 1) {
            for (Neural neural : outputLayerNeurals) {
                double[] edgeWeights = neural.getEdgeWeight();
                for (int i = 0; i < invisibleLayerNeurals.size(); i++) {
                    if ((invisibleLayerNeurals.get(i).getWeight() > 0 && result > 0) || (invisibleLayerNeurals.get(i).getWeight() < 0 && result < 0)) {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] += 0.001 * invisibleLayerNeurals.get(i).getWeight();
                    } else {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] -= 0.001 * invisibleLayerNeurals.get(i).getWeight();
                    }
                }
                neural.setEdgeWeight(edgeWeights);
            }
            for (Neural neural : invisibleLayerNeurals) {
                double[] edgeWeights = neural.getEdgeWeight();
                for (int i = 0; i < inputLayerNeurals.size(); i++) {
                    if ((inputLayerNeurals.get(i).getWeight() > 0 && result > 0) || (inputLayerNeurals.get(i).getWeight() < 0 && result < 0)) {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] += 0.001 * inputLayerNeurals.get(i).getWeight();
                    } else {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] -= 0.001 * inputLayerNeurals.get(i).getWeight();
                    }
                }
                neural.setEdgeWeight(edgeWeights);
            }
        } else {
            for (Neural neural : outputLayerNeurals) {
                double[] edgeWeights = neural.getEdgeWeight();
                for (int i = 0; i < invisibleLayerNeurals.size(); i++) {
                    if ((invisibleLayerNeurals.get(i).getWeight() > 0 && result > 0) || (invisibleLayerNeurals.get(i).getWeight() < 0 && result < 0)) {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] += 0.000001 * invisibleLayerNeurals.get(i).getWeight();
                    } else {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] -= 0.000001 * invisibleLayerNeurals.get(i).getWeight();
                    }
                }
                neural.setEdgeWeight(edgeWeights);
            }
            for (Neural neural : invisibleLayerNeurals) {
                double[] edgeWeights = neural.getEdgeWeight();
                for (int i = 0; i < inputLayerNeurals.size(); i++) {
                    if ((inputLayerNeurals.get(i).getWeight() > 0 && result > 0) || (inputLayerNeurals.get(i).getWeight() < 0 && result < 0)) {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] += 0.0000001 * inputLayerNeurals.get(i).getWeight();
                    } else {
                        if (Math.abs(edgeWeights[i]) < 2)
                            edgeWeights[i] -= 0.0000001 * inputLayerNeurals.get(i).getWeight();
                    }
                }
                neural.setEdgeWeight(edgeWeights);
            }
        }
        BPNeuralNetwork.printAllEdges();
//        recalculateWeight();
    }

  /*  public static void feedback() {

    }*/

    public static double predict(double[] sources) {
        for (int i = 0; i < invisibleLayerNeurals.size(); i++) {
            double tempWeight = 0;
            for (int j = 0; j < inputLayerNeurals.size(); j++) {
                tempWeight += sources[j] * invisibleLayerNeurals.get(i).getEdgeWeight()[j];
                System.out.println(tempWeight);
            }
            invisibleLayerNeurals.get(i).setWeight(tempWeight);
        }

        double output = 0;
        for (int i = 0; i < invisibleLayerNeurals.size(); i++) {
            output += invisibleLayerNeurals.get(i).getWeight() * outputLayerNeurals.get(0).getEdgeWeight()[i];
            System.out.println(invisibleLayerNeurals.get(i).getWeight());
            System.out.println(outputLayerNeurals.get(0).getEdgeWeight()[i]);
            System.out.println(output);
        }
        System.out.println("predict result->" + output);
        return output;
    }

    public static double calculateWeight(double[] sources) {
        for (int i = 0; i < invisibleLayerNeurals.size(); i++) {
            double tempWeight = 0;
            for (int j = 0; j < inputLayerNeurals.size(); j++) {
                tempWeight += sources[j] * invisibleLayerNeurals.get(i).getEdgeWeight()[j];
            }
            invisibleLayerNeurals.get(i).setWeight(tempWeight);
        }

        double weight = 0;
        for (int i = 0; i < invisibleLayerNeurals.size(); i++) {
            weight += invisibleLayerNeurals.get(i).getWeight() * outputLayerNeurals.get(0).getEdgeWeight()[i];
        }
        return weight;
    }

//    public static void recalculateWeight(){
//
//    }
}
