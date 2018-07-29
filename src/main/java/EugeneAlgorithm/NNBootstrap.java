package EugeneAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class NNBootstrap {
    public static ArrayList<Integer> inputNeurals = new ArrayList<Integer>();
    public static ArrayList<Integer> invisibleNeurals = new ArrayList<Integer>();
    public static ArrayList<Integer> outputNeurals = new ArrayList<Integer>();

    private static String recordFilePath = "data/record.txt";//训练数据备份，下次不用再次训练，直接从这里初始化边的权重
    private static String dataFilePath = "data/data.txt";//用来进行预测的数据源，通过与训练集的比较，决定相应输入神经元的值为0或1
    private static String trainingFilePath = "data/training.txt";//用来进行训练的数据源

   /* static {  //Protection
        File recordFile = new File(recordFilePath);
        File trainingFile = new File(trainingFilePath);
        File dataFile = new File(dataFilePath);
        if (!recordFile.exists()) {
            if (trainingFile.exists()) {
                if (dataFile.exists()) {
                    try {
                        BufferedReader trainingReader = new BufferedReader(new FileReader(trainingFile));
                        String line = "";
                        boolean isFirst = true;
                        while ((line = trainingReader.readLine()) != null) {
                            StringTokenizer _StringTokenizer = new StringTokenizer(line, "#");
                            String data = _StringTokenizer.nextToken();
                            String result = _StringTokenizer.nextToken();
                            StringTokenizer __StringTokenizer = new StringTokenizer(data, " ");
                            if (isFirst) {
                                int size = __StringTokenizer.countTokens();

                                isFirst = false;
                            }
                            while (__StringTokenizer.hasMoreTokens()) {

                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("缺少预测集");
                    System.exit(-1);
                }
            } else {
                System.out.println("缺少训练集");
                System.exit(-1);
            }
        } else {

        }
    }*/

    public static void main(String[] args) {
        //BPNeuralNetwork.predict();

        //test :  training & feedback
        try {
            boolean isFirst = true;
            int size = 0;

            for (int i = 0; i < 1000; i++) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("data/training.txt")));
                String line = "";
                double _sources[];
                double _result;
                while ((line = bufferedReader.readLine()) != null) {//every training data only train once
                    StringTokenizer stringTokenizer = new StringTokenizer(line, "#");
                    String sources = stringTokenizer.nextToken();
                    _result = Double.parseDouble(stringTokenizer.nextToken());
                    StringTokenizer sourcesTokenizer = new StringTokenizer(sources, " ");
                    size = sourcesTokenizer.countTokens();
                    if (isFirst) {
                        BPNeuralNetwork.init(size);
//                    BPNeuralNetwork.printAllNeurals();
                        BPNeuralNetwork.printAllEdges();
                        isFirst = false;
                    }
                    _sources = new double[size];
                    int count = 0;
                    while (sourcesTokenizer.hasMoreTokens()) {
                        _sources[count] = Double.parseDouble(sourcesTokenizer.nextToken());
                        count++;
                    }
                    System.out.println("#############Train#############");
//                BPNeuralNetwork.train(10000, _sources, _result);//集中训练
                    BPNeuralNetwork.train(_sources, _result);
//                BPNeuralNetwork.printAllNeurals();

                }
                bufferedReader.close();
            }
//            BPNeuralNetwork.predict(new double[]{1,1,0,0,1,0});
            BPNeuralNetwork.predict(new double[]{0,0,0,0,0,1});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
