package cn.itcast.core.service;

import cn.itcast.core.dao.user.UserDao;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
@SuppressWarnings("all")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserDao userDao;

    @Override
    public PageResult search(Integer page, Integer rows, UserInfo userInfo) {
        PageHelper.startPage(page,rows);
        Page<UserInfo> page1 = (Page<UserInfo>) userDao.selectUserInfo();
        return new PageResult(page1.getTotal(),page1.getResult());
    }

    @Override
    public void outExcel() {
        List<List<String>> mapList=new ArrayList<>();
        //1,用户id
        List<String> ids =userDao.selectIdfromUser();
        System.out.println(ids);
        mapList.add(ids);

        //2.yonghu ming
        List<String> userList =userDao.selectUserNamefromUser();
        System.out.println(userList);
        mapList.add(userList);

        //3性别
        List<String>  sexList =userDao.selectSexfromUser();
        List<String>  sexName=new ArrayList<>();
        for (String s : sexList) {
            if ("1".equals(s)){
                sexName.add("男");
            }else{
                sexName.add("女");
            }
        }
        System.out.println(sexList);
        System.out.println(sexName);
        mapList.add(sexName);
        //4,订单编号
        List<String> orderIdList =userDao.selectOrderIdfromUser();
        System.out.println(orderIdList);
        mapList.add(orderIdList);
        //5,商品名称
        List<String> goodsNameList = userDao.selectGoodsNamefromUser();
        System.out.println(goodsNameList);
        mapList.add(goodsNameList);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("0");
        for (int i = 0; i < 9; i++) { sheet.setColumnWidth(i, 4300);
        }
        /**
         * 单元格 样式
         * */
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中

        /**
         * 标题样式 样式
         */
        XSSFFont titleFont = wb.createFont();
        titleFont.setFontHeight(24);
        titleFont.setBold(true);
        CellStyle titleCellStyle = wb.createCellStyle();
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
        titleCellStyle.setFont(titleFont);

        /**
         * 主 标题 在这里插入主标题
         */
        Row titleRow;
        Cell titleCell;
        sheet.addMergedRegion(new CellRangeAddress((short) 0, (short) 2, (short) 0, (short) 4));
        for (int i = 0; i <= 2; i++) { titleRow = sheet.createRow(i);
            for (int j = 0; j < mapList.size(); j++) { titleCell = titleRow.createCell(j);
                titleCell.setCellType(CellType.STRING);
                titleCell.setCellStyle(titleCellStyle);
                titleCell.setCellValue("用户详情统计表");
            } } /**
         * 列 标题 在这里插入标题
         */

        String[] names={"用户ID","用户名称","性别","订单编号","商品名称"};
        Row rowLabel;
        Cell cellLabel;
        for (int i = 3; i < 4; i++) {
            rowLabel = sheet.createRow(i);
            for (int j = 0; j < mapList.size(); j++) {
                cellLabel = rowLabel.createCell(j);
                cellLabel.setCellType(CellType.STRING);
                cellLabel.setCellStyle(cellStyle);
                cellLabel.setCellValue(names[j]);
            } }

        /**
         * 列 数据 在这里插入数据
         */
        Row rowCheck;
        Cell cellCheck;
        List<String> ids1 = (List<String>) mapList.get(0);
        for (int i = 3; i < ids1.size()+3; i++) {
            rowCheck = sheet.createRow((i + 1));
            for (int j = 0; j < mapList.size(); j++) { cellCheck = rowCheck.createCell(j);
                cellCheck.setCellType(CellType.STRING);
                cellCheck.setCellStyle(cellStyle);
                cellCheck.setCellValue(mapList.get(j).get(i-3));
            } }


        /**
         * 页脚
         */
        setExcelFooterName("测试", 0, wb);

        /**
         * 进行导出
         */
        exportOutPutExcel("F:\\importExcl\\import.xlsx", wb);

    }

    /**
     * 设置Excel页脚
     */
    public void setExcelFooterName(String customExcelFooterName, int setExcelFooterNumber, XSSFWorkbook wb) {
        wb.setSheetName(setExcelFooterNumber, customExcelFooterName);
    } /**
     * 输出流 导出Excel到桌面
     */
    public void exportOutPutExcel(String exportPositionPath, XSSFWorkbook wb) {
        try {
            File file = new File(exportPositionPath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
