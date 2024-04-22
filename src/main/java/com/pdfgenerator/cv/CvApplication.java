package com.pdfgenerator.cv;


import com.pdfgenerator.cv.models.Education;
import com.pdfgenerator.cv.models.Experience;
import com.pdfgenerator.cv.models.PersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CvApplication implements CommandLineRunner {

	@Autowired
	private TemplateEngine templateEngine;
	public static void main(String[] args) {
		SpringApplication.run(CvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		System.out.print("Enter email: ");
		String email = scanner.nextLine();
		System.out.print("Enter phone: ");
		String phone = scanner.nextLine();

		PersonalInfo personalInfo = new PersonalInfo(name,email,phone);

		List<Education> educations = new ArrayList<>();
		for (int i = 1; i <= 2; i++) { // Assuming 2 educational experiences
			System.out.println("Enter education #" + i + " details:");
			System.out.print("Degree: ");
			String degree = scanner.nextLine();
			System.out.print("Institution: ");
			String institution = scanner.nextLine();
			System.out.print("Year: ");
			String year = scanner.nextLine();
			educations.add(new Education(degree, institution, year));
		}

		List<Experience> experiences = new ArrayList<>();
		for (int i = 1; i <= 2; i++) { // Assuming 2 work experiences
			System.out.println("Enter work experience #" + i + " details:");
			System.out.print("Position: ");
			String position = scanner.nextLine();
			System.out.print("Company: ");
			String company = scanner.nextLine();
			System.out.print("Duration: ");
			String duration = scanner.nextLine();
			System.out.print("Description: ");
			String description = scanner.nextLine();
			experiences.add(new Experience(position, company, duration, description));
		}

		List<String> skills = new ArrayList<>();
		System.out.print("Enter skills (comma-separated): ");
		String skillsInput = scanner.nextLine();
		String[] skillsArray = skillsInput.split(",");
		for (String skill : skillsArray) {
			skills.add(skill.trim());
		}

		String htmlContent = generateHTML(personalInfo, educations, experiences, skills);

		System.out.print("Enter output file name (e.g., cv.pdf): ");
		String outputFileName = scanner.nextLine();

	
		convertToPDF(htmlContent,outputFileName);

		scanner.close();

	}

	private String generateHTML(PersonalInfo personalInfo, List<Education> educations, List<Experience> experiences, List<String> skills) {
		Context context = new Context();
		context.setVariable("personalInfo", personalInfo);
		context.setVariable("educations", educations);
		context.setVariable("experiences", experiences);
		context.setVariable("skills", skills);
		System.out.println(templateEngine.process("template.html",context));
		return templateEngine.process("template.html",context);
	}

	private void convertToPDF(String htmlContent, String outputFileName) throws Exception {
		try (FileOutputStream outputStream = new FileOutputStream(outputFileName)) {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(outputStream);
			System.out.println("CV generated successfully at: " + outputFileName);
		}
	}

//	private String generateHTML(String name, String email) {
//	//	TemplateEngine templateEngine = new SpringTemplateEngine();
//		Context context = new Context();
//		context.setVariable("name",name);
//		context.setVariable("email",email);
//
//		System.out.println(templateEngine.process("template.html",context));
//		return templateEngine.process("template.html",context);
//
//	}



}
