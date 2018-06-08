import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("D:\\Program Files\\kafka\\topicData\\0\\__consumer_offsets-3");
//        file.delete();
        for (File file1 : file.listFiles()) {
            System.out.println(file1);
            boolean delete = file1.delete();
            System.out.println("file:"+file1.getPath()+"?"+delete);
        }
        boolean delete = file.delete();
        System.out.println(file.getPath()+"?"+delete);

    }


}
