/**
 * Copyright (C) 2011 ArtiVisi Intermedia <info@artivisi.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.artivisi.kasbon.ui.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.artivisi.kasbon.domain.Karyawan;
import com.artivisi.kasbon.service.KasbonService;

@Controller
public class KaryawanController {
	
	@Autowired private KasbonService kasbonService;
	
	@RequestMapping("/karyawan/json")
	@ResponseBody
	public List<Karyawan> jsonListKaryawan(){
		return kasbonService.findAllKaryawan(0, 100);
	}
	
	@RequestMapping("/karyawan/list")
	public ModelMap list(@RequestParam(required=false)String nama){
		List<Karyawan> daftarKaryawan = new ArrayList<Karyawan>();
		
		if(StringUtils.hasText(nama)) {
			daftarKaryawan.addAll(kasbonService.findKaryawanByNama(nama));
		} else {
			daftarKaryawan.addAll(kasbonService.findAllKaryawan(0, 10));
		}
		
		ModelMap mm = new ModelMap();
		
		mm.addAttribute("karyawanList", daftarKaryawan);
		
		return mm;
	}
	
	@RequestMapping(value="/karyawan/form", method=RequestMethod.GET)
	public ModelMap displayForm(@RequestParam(required=false) Long id){
		ModelMap mm = new ModelMap();
		
		Karyawan k = kasbonService.findKaryawanById(id);
		
		if(k == null){
			k = new Karyawan();
		}
		
		mm.addAttribute(k);
		
		return mm;
	}
	
	@RequestMapping(value="/karyawan/form", method=RequestMethod.POST)
	public String processForm(@ModelAttribute @Valid Karyawan k, 
			BindingResult errors, 
			SessionStatus status){
		
		if(errors.hasErrors()) {
			return "/karyawan/form";
		}
		
		kasbonService.save(k);
		
		status.setComplete();
		return "redirect:list";
		
	}
}
