package com.rent.app.security.services.export;

import com.rent.app.models.Rent;

import java.io.ByteArrayInputStream;
import java.util.Map;

public interface ExportPdfService {
    ByteArrayInputStream exportReceiptPdf(String templateName, Map<String, Object> data);
    Map<String, Object> createReportData(Rent rent);
}
