package neu.wzb.XlsxDemo;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Test {

	public static double [][] Demo(double a[][]){
		double c[][]=new double[3][3];
		c=a;
		c[0][0]=2;
		return c;
	}
	public static void main(String[] args) {
//		int k=0;
//		double a[][]=new double[3][3];
//		for(int i=0;i<3;i++){
//			for(int j=0;j<3;j++){
//				a[i][j]=k++;
//			}
//		}
//		Test.Demo(a);
//		
//		for(int i=0;i<3;i++){
//			for(int j=0;j<3;j++){
//				System.out.print (a[i][j]+"  ");
//			}
//			System.out.println();
		
		double a=3.23232;
		System.out.println(a);
		DecimalFormat df=new DecimalFormat("#.000");
		System.out.println(a);
		//}
		
		
		Map<String,String> map=new TreeMap<String, String>();
		map.put("key1", "1");
		map.put("key2", "2");
		map.put("key3", "3");
		map.put("key4", "4");
		for(Map.Entry<String, String> entry:map.entrySet()){
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
	}

}
