package org.zerock.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dataFormat, false));
	}

	@RequestMapping("")
	public void basic() {
		log.info("basic....");
	}

	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("" + dto);
		return "ex01";
	}

	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name : " + name);
		log.info("age : " + age);
		return "ex02";
	}

	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids : " + ids);
		return "ex02List";
	}

	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("array ids : " + Arrays.toString(ids));

		return "ex02Array";
	}

	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos : " + list);
		return "ex02Bean";
	}

	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo : " + todo);
		return "ex03";
	}

	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		//java Bean객체의 규칙에 맞는 객체는 다시 화면으로 객체를 전달한다
		//하지만 기본자료형은 반환하지 않는다 그래서 ModelAttriabute어노테이션을 이용하여 반환한다
		log.info("dto : " + dto);
		log.info("page : " + page);

		return "/sample/ex04";
	}

	@GetMapping("/ex05") // 리턴값이 void인경우 url를 jsp파일 이름으로 사용한다
	public void ex05() {
		log.info("/ex05.............");
	}
	
	@GetMapping("/ex06") //com.fasterxml.jackson.core라이브러리를 이용하여 json형태로 반환한다
	@ResponseBody
	public SampleDTO ex06() {
		log.info("/ex06.............");
		
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		return dto;
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("/ex07....");
		
		//{"name" : 홍길동}
		String msg = "{\"name\": \"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload...");
	}
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> {
			log.info("-------------------------");
			try {
				log.info("name : "+ new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"EUC-KR"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("name : "+ file.getSize());
		});
	}

}
