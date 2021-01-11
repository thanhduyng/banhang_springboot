package learncode.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import learncode.spring.dto.SlidesDTO;
import learncode.spring.model.Slides;
import learncode.spring.repository.SlidesRepository;
import learncode.spring.service.SlidesService;

@Controller
@RequestMapping("/slides")
public class SlidesController {
	
	@Autowired
	SlidesService slidesService;
	
	@Autowired
	SlidesRepository slidesRepository;

	@GetMapping("/")
	@PreAuthorize("hasPermission('', 'themslides')")
	public String addOrEdit(ModelMap model) {
		SlidesDTO slidesDTO = new SlidesDTO();
		model.addAttribute("SLIDESDTO", slidesDTO);
		model.addAttribute("ACTION", "/slides/saveOrUpdate");
		return "/admin/register-slides";
	}
	
	@RequestMapping("/list")
	@PreAuthorize("hasPermission('', 'danhsachslides')")
	public String list(ModelMap model) {
		model.addAttribute("LIST_SLIDES", slidesService.findAll());
		return "/admin/view-slides";
	}
	
	@GetMapping("/page/{pageNumber}")
	@PreAuthorize("hasPermission('', 'danhsachslides')")
	public String showEmployeePage(Model mode, HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("slideslist");
		int pagesize = 3;
		List<Slides> list = (List<Slides>) slidesService.findAll();
		/* mode.addAttribute("listslides", slidesRepository.findAll()); */
		System.out.println(list.size());

		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("slideslist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/slides/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("LIST_SLIDES", pages);

		return "/admin/view-slides";
	}
	
	@PostMapping("/saveOrUpdate")
	public String save(HttpServletRequest request, ModelMap model, @Valid @ModelAttribute("SLIDESDTO") SlidesDTO dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/admin/register-slides";
		}
		Optional<Slides> optionalProduct = slidesService.findById(dto.getId());
		Slides slides = null;
		String image = "Logo.png";
		Path path = Paths.get("uploads/");
		if (optionalProduct.isPresent()) {
			// save

			if (dto.getImg().isEmpty()) {
				image = optionalProduct.get().getImg();

			} else {
				try {
					InputStream inputStream = dto.getImg().getInputStream();
					Files.copy(inputStream, path.resolve(dto.getImg().getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					image = dto.getImg().getOriginalFilename().toString();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		} else {
			// add new
			if (!dto.getImg().isEmpty()) {
				try {
					InputStream inputStream = dto.getImg().getInputStream();
					Files.copy(inputStream, path.resolve(dto.getImg().getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					image = dto.getImg().getOriginalFilename().toString();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		}

//		products = new Product(dto.getId(), dto.getName(), image, dto.getSku(), dto.getPrice(), new Category(dto.getId_category(),null, ""));
		slides = new Slides(dto.getId(), image, dto.getCaption(), dto.getContent());
		slidesService.save(slides);
		request.getSession().setAttribute("slideslist", null);
		return "/admin/register-slides";
	}
	
	@RequestMapping("/edit/{id}")
	@PreAuthorize("hasPermission('', 'suaslides')")
	public String edit(ModelMap model, HttpServletRequest request, @PathVariable(name = "id") Integer id) {
		Optional<Slides> slOptional = slidesService.findById(id);
		SlidesDTO dto = null;
		if (slOptional.isPresent()) {
			Slides pt = slOptional.get();
			File file = new File("uploads/" + pt.getImg());
			FileInputStream input;
			try {
				input = new FileInputStream(file);
				MultipartFile mutiPhoto = new MockMultipartFile("file", file.getName(), "text/plain",
						IOUtils.toByteArray(input));

				dto = new SlidesDTO(pt.getId(),mutiPhoto, pt.getCaption(), pt.getContent());

			} catch (FileNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			model.addAttribute("SLIDESDTO", dto);
			request.getSession().setAttribute("slideslist", null);
		} else {
			model.addAttribute("SLIDESDTO", new SlidesDTO());
		}
		model.addAttribute("ACTION", "/slides/saveOrUpdate");
		return "/admin/register-slides";
	}
	
	@RequestMapping("/delete/{id}")
	@PreAuthorize("hasPermission('', 'xoaslides')")
	public String deleteProduct(HttpServletRequest request, @PathVariable(name = "id") int id) {
		slidesService.deleteById(id);;
		request.getSession().setAttribute("slideslist", null);
		return "redirect:/slides/page/{id}";
	}
}
