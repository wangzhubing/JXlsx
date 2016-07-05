package neu.wzb.XlsxDemo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelNormal {
	private final static int ColumnNum = 6;
	private final static int RowNum = 36;
	private final double max[] = new double[ColumnNum-1];
	private final double min[] = new double[ColumnNum-1];

	public double[] getMax() {
		return max;
	}

	public double[] getMin() {
		return min;
	}

	// 将特定列的数据放入而写excel 
	public void creatFillExcel(double arr[][], double arr1[]) {
		String targetfile = "C:/normal.xlsx";
		Workbook workbook = new XSSFWorkbook();
		try {

			Sheet sheet = workbook.createSheet("new sheet");
			Row firstRow = sheet.createRow(0);
			Cell[] firstCells = new XSSFCell[ColumnNum];
			String[] title = { "PriceIndex", "TradeLine", "WeatherIndex",
					"CarNum", "Finished Steel", "IndestrialElecCom" };
			for (int j = 0; j < title.length; j++) {
				firstCells[j] = firstRow.createCell(j);
				firstCells[j].setCellValue(new XSSFRichTextString((title[j])));
			}
			for (int i = 0; i < RowNum; i++) {
				Row row = sheet.createRow(i + 1);
				for (int j = 0; j < ColumnNum-1; j++) {
					Cell cell = row.createCell(j);
					cell.setCellValue(arr[j][i]);
				}
				Cell cell=row.createCell(ColumnNum-1);
				cell.setCellValue(arr1[i]);

			}
			OutputStream outs = new FileOutputStream(targetfile);
			workbook.write(outs);

			outs.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 从excel 表格中读取全部数据
	public double[][] getArryFromExcel(String file) {
		String fileName = file;
		double arr[][] = new double[ColumnNum-1 ][RowNum];
		try {
			InputStream input = new FileInputStream(fileName);
			Workbook workbook = new XSSFWorkbook(input);
			Sheet sheet = workbook.getSheetAt(0);
			for (int rowNum = sheet.getFirstRowNum() + 1; rowNum <=sheet
					.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				for (int column = row.getFirstCellNum(); column < row
						.getLastCellNum()-1; column++) {
					Cell i = row.getCell(column);
					double n = i.getNumericCellValue();
					arr[column][rowNum-1] = n;
				}
			}
			input.close();

			
			
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		return arr;
	}

	// 从excel表中读取指定列的数据 //num为需要的列数-1
	public double[] getPointArry(String file, int num) {
		double[] arr2 = new double[RowNum];
		try {
			InputStream is = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				Cell cell = row.getCell(num);
				double n = cell.getNumericCellValue();
				DecimalFormat df = new DecimalFormat("#.000");
				arr2[i - 1] = Double.parseDouble(df.format(n));
			}
			is.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return arr2;
	}
//得到每一二维数组最大值最小值
	public void getMaxandMin(double arr[][]) {
		double[][] arr1 = new double[ColumnNum-1][RowNum];
		for (int i = 0; i < ColumnNum-1; i++) {
			for (int j = 0; j < RowNum; j++) {
				arr1[i][j] = arr[i][j];
			}

		}
		for (int i = 0; i < arr1.length; i++) {
			Arrays.sort(arr1[i]);
			max[i] = arr1[i][arr1[i].length - 1];
			min[i] = arr1[i][0];
		}
	}
//实现归一化处理
	public double[][] getNormalData(double arr[][], double max[], double min[]) {
		double[][] arr1 = new double[ColumnNum-1][RowNum];
		double a;
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1[i].length; j++) {
				a = (arr[i][j] - min[i]) / ((double) (max[i] - min[i]));
				DecimalFormat df = new DecimalFormat("#.000");
				arr1[i][j] = Double.parseDouble(df.format(a));
			}

		}
		return arr1;
	}

	public static void main(String[] args) {
		ExcelNormal exn = new ExcelNormal();
		double arr[][] = new double[ColumnNum-1][RowNum];
		double arr1[][] = new double[ColumnNum-1][RowNum];
		double[] arr2 = new double[RowNum];
		arr = exn
				.getArryFromExcel("C:/Users/Administrator/Desktop/数据行/traindata1.xlsx");
		exn.getMaxandMin(arr);

		arr1 = exn.getNormalData(arr, exn.getMax(), exn.getMin());

		arr2 = exn.getPointArry(
				"C:/Users/Administrator/Desktop/数据行/traindata1.xlsx", 5);
		exn.creatFillExcel(arr1, arr2);// 向excel中写入数据
	}

}
