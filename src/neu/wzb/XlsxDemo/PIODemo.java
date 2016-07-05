package neu.wzb.XlsxDemo;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class PIODemo {
	public static void main(String[] args) {
		readXml("c:/traindata1.xlsx");
		System.out.println("-------------");
//		readXml("d:/test2.xls");
 	}
	public static void readXml(String fileName){
		boolean isE2007 = false;	//�ж��Ƿ���excel2007��ʽ
		if(fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName);	//����������
			Workbook wb  = null;
			//�����ļ���ʽ(2003����2007)����ʼ��
			if(isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);		//��õ�һ����
			Iterator<Row> rows = sheet.rowIterator();	//��õ�һ�����ĵ�����
			while (rows.hasNext()) {
				Row row = rows.next();	//���������
				System.out.print("Row #" + row.getRowNum());	//����кŴ�0��ʼ
				Iterator<Cell> cells = row.cellIterator();	//��õ�һ�еĵ�����
				while (cells.hasNext()) {
					Cell cell = cells.next();
					System.out.println("  "+"Cell #" + cell.getColumnIndex());
					switch (cell.getCellType()) {	//����cell�е��������������
					case HSSFCell.CELL_TYPE_NUMERIC:
						System.out.println(cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:
						System.out.println(cell.getStringCellValue());
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						System.out.println(cell.getBooleanCellValue());
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						System.out.println(cell.getCellFormula());
						break;
					default:
						System.out.println("unsuported sell type");
					break;
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
