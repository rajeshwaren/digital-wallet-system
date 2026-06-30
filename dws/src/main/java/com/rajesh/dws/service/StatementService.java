package com.rajesh.dws.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.rajesh.dws.entity.Transaction;
import com.rajesh.dws.repository.TransactionRepository;

@Service
public class StatementService {

    @Autowired
    private TransactionRepository transactionRepository;

    public byte[] generateStatement(
            String email) {

        try {

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            Document document =
                    new Document();

            PdfWriter.getInstance(
                    document,
                    out
            );

            document.open();

            document.add(
                    new Paragraph(
                            "Digital Wallet Statement"
                    )
            );

            document.add(
                    new Paragraph(
                            "User: " + email
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            PdfPTable table =
                    new PdfPTable(5);

            table.addCell("ID");
            table.addCell("Type");
            table.addCell("Amount");
            table.addCell("Status");
            table.addCell("Time");

            List<Transaction> transactions =
                    transactionRepository
                            .findAll();

            for(Transaction tx :
                    transactions) {

                if(email.equals(
                        tx.getSenderEmail())
                   ||
                   email.equals(
                        tx.getReceiverEmail())) {

                    table.addCell(
                            tx.getId().toString()
                    );

                    table.addCell(
                            tx.getType()
                    );

                    table.addCell(
                            tx.getAmount()
                              .toString()
                    );

                    table.addCell(
                            tx.getStatus()
                    );
                    table.addCell(
                        tx.getCreatedAt().toString()
                    );
                }
            }

            document.add(table);

            document.close();

            return out.toByteArray();

        } catch(Exception e) {

            throw new RuntimeException(e);
        }
    }
}