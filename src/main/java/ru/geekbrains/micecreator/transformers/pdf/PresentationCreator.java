package ru.geekbrains.micecreator.transformers.pdf;

import lombok.NoArgsConstructor;
import org.apache.commons.text.WordUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import ru.geekbrains.micecreator.dto.complex.presentation.AccHotel;
import ru.geekbrains.micecreator.dto.complex.presentation.AccLocation;
import ru.geekbrains.micecreator.dto.complex.presentation.AccRoom;
import ru.geekbrains.micecreator.dto.complex.presentation.EventDesc;
import ru.geekbrains.micecreator.dto.complex.presentation.EventPlace;
import ru.geekbrains.micecreator.dto.complex.presentation.TourPresentation;
import ru.geekbrains.micecreator.exceptions.PresentationException;
import ru.geekbrains.micecreator.utils.AppUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
@NoArgsConstructor
public class PresentationCreator {

	private static final Logger logger = LogManager.getLogger(PresentationCreator.class);

	private PDFont MAIN_FONT;
	private final String MAIN_FONT_URL = "classpath:pdf_creator/timesnewromanpsmt.ttf";
	private final String BACKGROUND_URL = "classpath:pdf_creator/palm.jpg";
	private final String AIRLINE_URL = "classpath:pdf_creator/airline.jpg";
	private final Integer TITLE_FONT_SIZE = 30;
	private final Integer SUBTITLE_FONT_SIZE = 24;
	private final Integer STRING_FONT_SIZE = 12;
	private final Integer STRING_PADDING = 20;

	public void createPresentation(TourPresentation presentation, String path) {
		try (PDDocument doc = new PDDocument()) {
			MAIN_FONT = PDType0Font.load(doc, ResourceUtils.getFile(MAIN_FONT_URL));

			PDPage titlePage = new PDPage();
			doc.addPage(titlePage);

			try (PDPageContentStream cont = new PDPageContentStream(doc, titlePage)) {

				PDImageXObject pdImage = PDImageXObject.createFromFileByExtension(ResourceUtils.getFile(BACKGROUND_URL), doc);
				cont.drawImage(pdImage, 0, 0, titlePage.getMediaBox().getWidth(), titlePage.getMediaBox().getHeight());

				String title = "ОПИСАНИЕ СОСТАВЛЯЮЩИХ ТУРА";
				String country = String.format("СТРАНА: %s", presentation.getCountry());
				String dates = String.format("ДАТЫ: %s - %s", presentation.getStartDate(), presentation.getEndDate());

				float titleLength = MAIN_FONT.getStringWidth(title) / 1000 * TITLE_FONT_SIZE;
				float countryLength = MAIN_FONT.getStringWidth(country) / 1000 * TITLE_FONT_SIZE;
				float datesLength = MAIN_FONT.getStringWidth(dates) / 1000 * TITLE_FONT_SIZE;
				float titleHeight = MAIN_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * TITLE_FONT_SIZE;

				cont.beginText();
				cont.setFont(MAIN_FONT, TITLE_FONT_SIZE);
				cont.setLeading(20f);
				cont.setRenderingMode(RenderingMode.FILL_STROKE);

				cont.newLineAtOffset((titlePage.getMediaBox().getWidth() - titleLength) / 2, 500);
				cont.showText(title);
				cont.endText();
				cont.setRenderingMode(RenderingMode.FILL);
				cont.beginText();
				cont.newLineAtOffset((titlePage.getMediaBox().getWidth() - countryLength) / 2, 500 - titleHeight * 2);
				cont.showText(country);
				cont.endText();
				cont.beginText();
				cont.newLineAtOffset((titlePage.getMediaBox().getWidth() - datesLength) / 2, 500 - titleHeight * 4);
				cont.showText(dates);
				cont.endText();
			}
			addAirlineInfo(presentation, doc);
			addAccommodationInfo(presentation, doc);
			addEventInfo(presentation.getEventRegions(), doc);
			addEventInfo(presentation.getEventHotels(), doc);
			doc.save(path);
		} catch (IOException e) {
			logger.error("Error occurred while preparing presentation file: {}", e);
			throw new PresentationException(e.getMessage());
		}
	}

	private void addAirlineInfo(TourPresentation presentation, PDDocument doc) throws IOException {
		if (presentation.getAirlines().isEmpty()) {
			return;
		}
		PDPage airlinePage = new PDPage();
		doc.addPage(airlinePage);
		PDPageContentStream cont = new PDPageContentStream(doc, airlinePage);
		String title = "АВИАКОМПАНИИ";
		float titleLength = MAIN_FONT.getStringWidth(title) / 1000 * TITLE_FONT_SIZE;
		int currentPosition = 0;
		for (int i = 0; i < presentation.getAirlines().size(); i++) {
			if (i % 2 == 0 && i != 0) {
				airlinePage = new PDPage();
				doc.addPage(airlinePage);
				cont.close();
				cont = new PDPageContentStream(doc, airlinePage);
			}
			if (i % 2 == 0) {
				PDImageXObject pdImage = PDImageXObject.createFromFileByExtension(ResourceUtils.getFile(AIRLINE_URL), doc);
				cont.drawImage(pdImage, 0, airlinePage.getMediaBox().getHeight() - (airlinePage.getMediaBox().getWidth() / 3),
						airlinePage.getMediaBox().getWidth(), airlinePage.getMediaBox().getWidth() / 3);

				currentPosition = 550;
				cont.beginText();
				cont.setFont(MAIN_FONT, TITLE_FONT_SIZE);
				cont.setLeading(20f);
				cont.setRenderingMode(RenderingMode.FILL_STROKE);
				cont.newLineAtOffset((airlinePage.getMediaBox().getWidth() - titleLength) / 2, currentPosition);
				cont.showText(title);
				cont.endText();
				currentPosition -= MAIN_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * TITLE_FONT_SIZE;
			}
			cont.beginText();
			cont.setFont(MAIN_FONT, SUBTITLE_FONT_SIZE);
			cont.setLeading(15f);
			cont.setRenderingMode(RenderingMode.FILL_STROKE);
			cont.newLineAtOffset(
					(airlinePage.getMediaBox().getWidth() - (MAIN_FONT.getStringWidth(presentation.getAirlines().get(i).getName())
					                                         / 1000 * SUBTITLE_FONT_SIZE)) / 2, currentPosition);
			cont.showText(presentation.getAirlines().get(i).getName());
			cont.endText();
			currentPosition -= MAIN_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * SUBTITLE_FONT_SIZE;
			if (AppUtils.isBlank(presentation.getAirlines().get(i).getDesc())) {
				continue;
			}
			cont.beginText();
			cont.setFont(MAIN_FONT, STRING_FONT_SIZE);
			cont.setLeading(15f);
			cont.setRenderingMode(RenderingMode.FILL);
			fillTextBlock(presentation.getAirlines().get(i).getDesc(), cont, currentPosition);
			cont.endText();
			currentPosition -= 230;
		}
		cont.close();
	}

	private void addAccommodationInfo(TourPresentation presentation, PDDocument doc) throws IOException {
		for (AccLocation location : presentation.getAccLocations()) {
			PDPage locationPage = new PDPage();
			doc.addPage(locationPage);
			PDPageContentStream cont = new PDPageContentStream(doc, locationPage);
			float currentPosition = fillTwoImages(cont, location.getImageLink(), null, locationPage, doc);
			fillTextPart(cont, locationPage, currentPosition, location.getName(), location.getDesc());
			cont.close();
			addHotelInfo(location.getAccHotels(), doc);
		}
	}

	private void addHotelInfo(List<AccHotel> hotels, PDDocument doc) throws IOException {
		for (AccHotel hotel : hotels) {
			PDPage hotelPage = new PDPage();
			doc.addPage(hotelPage);
			PDPageContentStream cont = new PDPageContentStream(doc, hotelPage);
			float currentPosition = fillTwoImages(cont, hotel.getImageOneLink(), hotel.getImageTwoLink(), hotelPage, doc);
			fillTextPart(cont, hotelPage, currentPosition, hotel.getName(), hotel.getDesc());
			cont.close();
			addRoomInfo(hotel.getAccRooms(), doc);
		}
	}

	private void addRoomInfo(List<AccRoom> accRooms, PDDocument doc) throws IOException {
		for (AccRoom room : accRooms) {
			PDPage roomPage = new PDPage();
			doc.addPage(roomPage);
			PDPageContentStream cont = new PDPageContentStream(doc, roomPage);
			float currentPosition = fillTwoImages(cont, room.getImageOneLink(), room.getImageTwoLink(), roomPage, doc);
			fillTextPart(cont, roomPage, currentPosition, room.getName(), room.getDesc());
			cont.close();
		}
	}

	private void addEventInfo(List<EventPlace> places, PDDocument doc) throws IOException {
		for (EventPlace place : places) {
			for (EventDesc event : place.getEvents()) {
				String title = String.format("%s: %s", place.getName(), event.getName());
				PDPage eventPage = new PDPage();
				doc.addPage(eventPage);
				PDPageContentStream cont = new PDPageContentStream(doc, eventPage);
				float currentPosition = fillTwoImages(cont, event.getImageOneLink(), event.getImageTwoLink(), eventPage, doc);
				fillTextPart(cont, eventPage, currentPosition, title, event.getDesc());
				cont.close();
			}
		}
	}

	private void fillTextBlock(String text, PDPageContentStream cont, float currentPosition) throws IOException {
		String[] wrT = WordUtils.wrap(text, 100).split("\\r?\\n");
		for (int y = 0; y < wrT.length; y++) {
			if (y == 0) {
				cont.newLineAtOffset(STRING_PADDING, currentPosition);
			} else {
				cont.newLine();
			}
			cont.showText(wrT[y]);
		}
	}

	private float fillTwoImages(PDPageContentStream cont, Path imageOnePath, Path imageTwoPath, PDPage page, PDDocument doc) throws IOException {
		String imageOneLink = imageOnePath == null ? null : imageOnePath.toString();
		String imageTwoLink = imageTwoPath == null ? null : imageTwoPath.toString();
		float currentPosition = page.getMediaBox().getHeight() - STRING_PADDING;
		if (imageOneLink != null && imageTwoLink != null) {
			PDImageXObject pdImageOne = PDImageXObject.createFromFileByExtension(ResourceUtils.getFile(imageOneLink), doc);
			cont.drawImage(pdImageOne, STRING_PADDING, currentPosition - (page.getMediaBox().getWidth() - STRING_PADDING) / 3,
					(page.getMediaBox().getWidth() - STRING_PADDING * 3) / 2,
					(page.getMediaBox().getWidth() - STRING_PADDING * 2) / 3);
			PDImageXObject pdImageTwo = PDImageXObject.createFromFileByExtension(ResourceUtils.getFile(imageTwoLink), doc);
			cont.drawImage(pdImageTwo, (page.getMediaBox().getWidth() + STRING_PADDING) / 2,
					currentPosition - (page.getMediaBox().getWidth() - STRING_PADDING) / 3,
					(page.getMediaBox().getWidth() - STRING_PADDING * 3) / 2,
					(page.getMediaBox().getWidth() - STRING_PADDING * 2) / 3);

			currentPosition -= (page.getMediaBox().getWidth()) / 3 + STRING_PADDING * 2;
		} else if (imageOneLink != null || imageTwoLink != null) {
			String imageLink = imageOneLink != null ? imageOneLink : imageTwoLink;
			PDImageXObject pdImage = PDImageXObject.createFromFileByExtension(ResourceUtils.getFile(imageLink), doc);
			cont.drawImage(pdImage, STRING_PADDING, currentPosition - (page.getMediaBox().getWidth() - STRING_PADDING * 2) / 3 * 2,
					page.getMediaBox().getWidth() - STRING_PADDING * 2,
					(page.getMediaBox().getWidth() - STRING_PADDING * 2) / 3 * 2);

			currentPosition -= ((page.getMediaBox().getWidth() + STRING_PADDING) / 3) * 2;
		} else {
			currentPosition -= STRING_PADDING;
		}
		return currentPosition;
	}

	private void fillTextPart(PDPageContentStream cont, PDPage page, float currentPosition, String title, String desc) throws IOException {
		cont.beginText();
		cont.setFont(MAIN_FONT, SUBTITLE_FONT_SIZE);
		cont.setLeading(15f);
		cont.setRenderingMode(RenderingMode.FILL_STROKE);
		cont.newLineAtOffset(
				(page.getMediaBox().getWidth() - (MAIN_FONT.getStringWidth(title)
				                                  / 1000 * SUBTITLE_FONT_SIZE)) / 2, currentPosition);
		cont.showText(title);
		cont.endText();
		currentPosition -= MAIN_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * SUBTITLE_FONT_SIZE;
		if (!AppUtils.isBlank(desc)) {
			cont.beginText();
			cont.setFont(MAIN_FONT, STRING_FONT_SIZE);
			cont.setLeading(15f);
			cont.setRenderingMode(RenderingMode.FILL);
			fillTextBlock(desc, cont, currentPosition);
			cont.endText();
		}
	}
}
