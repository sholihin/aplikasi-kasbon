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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.artivisi.kasbon.domain.Karyawan;
import com.artivisi.kasbon.service.KasbonService;

@Controller
public class KaryawanController {
	
	@Autowired private KasbonService kasbonService;
	
	@RequestMapping("/karyawan/list")
	public ModelMap list(){
		ModelMap mm = new ModelMap();
		return mm;
	}
	
	@RequestMapping(value="/karyawan/form", method=RequestMethod.GET)
	public ModelMap displayForm(){
		ModelMap mm = new ModelMap();
		
		Karyawan k = new Karyawan();
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
