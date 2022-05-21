package ru.geekbrains.micecreator.transformers.xls;

import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import ru.geekbrains.micecreator.dto.complex.estimate.AccommodationEstimate;
import ru.geekbrains.micecreator.dto.complex.estimate.FlightEstimate;
import ru.geekbrains.micecreator.dto.complex.estimate.HotelEventEstimate;
import ru.geekbrains.micecreator.dto.complex.estimate.RegionEventEstimate;
import ru.geekbrains.micecreator.dto.complex.estimate.TourEstimate;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Component
@NoArgsConstructor
public class EstimateCreator {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private final DateTimeFormatter withTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");


	public void createEstimate(TourEstimate tourEstimate, String path) {
		HSSFWorkbook estimate = new HSSFWorkbook();
		HSSFCellStyle styleMH = createMHStyle(estimate);
		HSSFCellStyle styleSH = createSHStyle(estimate, false);
		HSSFCellStyle styleSMH = createSHStyle(estimate, true);
		HSSFCellStyle styleLine = createLineStyle(estimate, false);
		HSSFCellStyle styleLineB = createLineStyle(estimate, true);

		List<Integer> countingRows = new ArrayList<>();
		String title = String.format("Тур №%s - %s", tourEstimate.getTour().getId(), tourEstimate.getTour().getCountry().getName());
		HSSFSheet sheet = estimate.createSheet(title);
		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 5000);
		int rowNum = 0;
		Row row = sheet.createRow(rowNum);
		row.setHeight((short) 2000);
		createCellsWithStyle(row, styleMH);
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8));
		row.getCell(0).setCellValue(title);
		rowNum++;

		row = sheet.createRow(rowNum);
		row.setHeight((short) 300);
		createCellsWithStyle(row, styleSH);
		row.getCell(0).setCellValue("Даты");
		row.getCell(1).setCellValue("Дата начала тура");
		row.getCell(4).setCellValue(formatter.format(tourEstimate.getTour().getStartDate()));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 3));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 8));
		rowNum++;

		row = sheet.createRow(rowNum);
		row.setHeight((short) 300);
		createCellsWithStyle(row, styleSH);
		row.getCell(1).setCellValue("Дата окончания тура");
		row.getCell(4).setCellValue(formatter.format(tourEstimate.getTour().getEndDate()));
		sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 3));
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 8));
		rowNum++;

		if (!tourEstimate.getAccommodations().isEmpty()) {
			row = sheet.createRow(rowNum);
			row.setHeight((short) 300);
			createCellsWithStyle(row, styleSMH);
			row.getCell(0).setCellValue("Размещение");
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8));
			rowNum++;

			row = sheet.createRow(rowNum);
			row.setHeight((short) 300);
			createCellsWithStyle(row, styleSH);
			row.getCell(0).setCellValue("Отель");
			row.getCell(1).setCellValue("Тип номера");
			row.getCell(2).setCellValue("Тип размещения");
			row.getCell(3).setCellValue("Дата заселения");
			row.getCell(4).setCellValue("Дата выезда");
			row.getCell(5).setCellValue("Кол-во ночей");
			row.getCell(6).setCellValue("Ст-ть за человека");
			row.getCell(7).setCellValue("Кол-во человек");
			row.getCell(8).setCellValue("ИТОГО");
			for (AccommodationEstimate accomm : tourEstimate.getAccommodations()) {
				rowNum++;
				row = sheet.createRow(rowNum);
				createCellsWithStyle(row, styleLine);
				row.setHeight((short) 300);
				row.getCell(0).setCellValue(accomm.getHotel().getName());
				row.getCell(1).setCellValue(accomm.getRoom().getName());
				row.getCell(2).setCellValue(accomm.getAccType().getName());
				row.getCell(3).setCellValue(formatter.format(accomm.getCheckInDate()));
				row.getCell(4).setCellValue(formatter.format(accomm.getCheckOutDate()));
				row.getCell(5).setCellValue(accomm.getNights());
				row.getCell(6).setCellValue(accomm.getPrice().doubleValue());
				row.getCell(7).setCellValue(accomm.getPax());
				row.getCell(8).setCellFormula(String.format("F%s*G%s*H%s", rowNum + 1, rowNum + 1, rowNum + 1));
				countingRows.add(rowNum + 1);
			}
			rowNum++;
		}

		if (!tourEstimate.getFlights().isEmpty()) {
			row = sheet.createRow(rowNum);
			row.setHeight((short) 300);
			createCellsWithStyle(row, styleSMH);
			row.getCell(0).setCellValue("Перелеты");
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8));
			rowNum++;

			row = sheet.createRow(rowNum);
			row.setHeight((short) 300);
			createCellsWithStyle(row, styleSH);
			row.getCell(0).setCellValue("Авиакомпания");
			row.getCell(2).setCellValue("Аэропорт вылета");
			row.getCell(3).setCellValue("Дата вылета");
			row.getCell(4).setCellValue("Дата прилета");
			row.getCell(5).setCellValue("Аэропорт прилета");
			row.getCell(6).setCellValue("Ст-ть за человека");
			row.getCell(7).setCellValue("Кол-во человек");
			row.getCell(8).setCellValue("ИТОГО");
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
			for (FlightEstimate flight : tourEstimate.getFlights()) {
				rowNum++;
				row = sheet.createRow(rowNum);
				row.setHeight((short) 300);
				createCellsWithStyle(row, styleLine);
				row.getCell(0).setCellValue(flight.getAirline().getName());
				row.getCell(2).setCellValue(flight.getDepartureAirport().getName());
				row.getCell(3).setCellValue(withTimeFormatter.format(flight.getDepartureDate()));
				row.getCell(4).setCellValue(withTimeFormatter.format(flight.getArrivalDate()));
				row.getCell(5).setCellValue(flight.getArrivalAirport().getName());
				row.getCell(6).setCellValue(flight.getPrice().doubleValue());
				row.getCell(7).setCellValue(flight.getPax());
				row.getCell(8).setCellFormula(String.format("G%s*H%s", rowNum + 1, rowNum + 1));
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
				countingRows.add(rowNum + 1);
			}
			rowNum++;
		}

		if (!tourEstimate.getHotelEvents().isEmpty()) {
			row = sheet.createRow(rowNum);
			row.setHeight((short) 300);
			createCellsWithStyle(row, styleSMH);
			row.getCell(0).setCellValue("Региональные эвенты");
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8));
			rowNum++;

			row = sheet.createRow(rowNum);
			row.setHeight((short) 300);
			createCellsWithStyle(row, styleSH);
			row.getCell(0).setCellValue("Регион");
			row.getCell(1).setCellValue("Региональная услуга");
			row.getCell(5).setCellValue("Дата эвента");
			row.getCell(6).setCellValue("Ст-ть за человека");
			row.getCell(7).setCellValue("Кол-во человек");
			row.getCell(8).setCellValue("ИТОГО");
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 4));
			for (RegionEventEstimate event : tourEstimate.getRegionEvents()) {
				rowNum++;
				row = sheet.createRow(rowNum);
				row.setHeight((short) 300);
				createCellsWithStyle(row, styleLine);
				row.getCell(0).setCellValue(event.getRegion().getName());
				row.getCell(1).setCellValue(event.getService().getName());
				row.getCell(5).setCellValue(formatter.format(event.getEventDate()));
				row.getCell(6).setCellValue(event.getPrice().doubleValue());
				row.getCell(7).setCellValue(event.getPax());
				row.getCell(8).setCellFormula(String.format("G%s*H%s", rowNum + 1, rowNum + 1));
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 4));
				countingRows.add(rowNum + 1);
			}
			rowNum++;
		}

		if (!tourEstimate.getHotelEvents().isEmpty()) {
			row = sheet.createRow(rowNum);
			row.setHeight((short) 300);
			createCellsWithStyle(row, styleSMH);
			row.getCell(0).setCellValue("Отельные эвенты");
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8));
			rowNum++;

			row = sheet.createRow(rowNum);
			row.setHeight((short) 300);
			createCellsWithStyle(row, styleSH);
			row.getCell(0).setCellValue("Отель");
			row.getCell(1).setCellValue("Отельная услуга");
			row.getCell(5).setCellValue("Дата эвента");
			row.getCell(6).setCellValue("Ст-ть за человека");
			row.getCell(7).setCellValue("Кол-во человек");
			row.getCell(8).setCellValue("ИТОГО");
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 4));
			for (HotelEventEstimate event : tourEstimate.getHotelEvents()) {
				rowNum++;
				row = sheet.createRow(rowNum);
				row.setHeight((short) 300);
				createCellsWithStyle(row, styleLine);
				row.getCell(0).setCellValue(event.getHotel().getName());
				row.getCell(1).setCellValue(event.getService().getName());
				row.getCell(5).setCellValue(formatter.format(event.getEventDate()));
				row.getCell(6).setCellValue(event.getPrice().doubleValue());
				row.getCell(7).setCellValue(event.getPax());
				row.getCell(8).setCellFormula(String.format("G%s*H%s", rowNum + 1, rowNum + 1));
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 4));
				countingRows.add(rowNum + 1);
			}
			rowNum++;
		}

		row = sheet.createRow(rowNum);
		row.setHeight((short) 400);
		row.createCell(7).setCellValue("ИТОГО ЗА ТУР:");
		row.createCell(8).setCellFormula(getTotalFormula(countingRows));
		row.getCell(7).setCellStyle(styleSH);
		row.getCell(8).setCellStyle(styleLineB);

		try (FileOutputStream out = new FileOutputStream(new File(path))) {
			estimate.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getTotalFormula(List<Integer> countingRows) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < countingRows.size(); i++) {
			sb.append("I");
			sb.append(countingRows.get(i));
			if (i < countingRows.size() - 1) {
				sb.append("+");
			}
		}
		return sb.toString();
	}

	private HSSFCellStyle createMHStyle(HSSFWorkbook estimate) {
		HSSFCellStyle style = estimate.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		Font font = estimate.createFont();
		font.setColor(IndexedColors.RED.getIndex());
		font.setFontHeight((short) 500);
		font.setBold(true);
		style.setFont(font);
		return style;
	}

	private HSSFCellStyle createSHStyle(HSSFWorkbook estimate, boolean isBold) {
		HSSFCellStyle style = estimate.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		Font font = estimate.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setFontHeight((short) 200);
		font.setBold(isBold);
		style.setFont(font);
		return style;
	}

	private HSSFCellStyle createLineStyle(HSSFWorkbook estimate, boolean isBold) {
		HSSFCellStyle style = estimate.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		Font font = estimate.createFont();
		font.setFontHeight((short) 200);
		font.setBold(isBold);
		style.setFont(font);
		return style;
	}


	private void createCellsWithStyle(Row row, HSSFCellStyle style) {
		for (int i = 0; i < 9; i++) {
			row.createCell(i);
			row.getCell(i).setCellStyle(style);
		}
	}
}
