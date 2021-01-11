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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.mock.web.MockMultipartFile;
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

import learncode.spring.dto.TintucDTO;
import learncode.spring.model.TinTuc;
import learncode.spring.service.TinTucService;

@Controller
@RequestMapping("/news")
public class TinTucController {

	@Autowired
	TinTucService tinTucService;

	@GetMapping("/")
//	@PreAuthorize("hasPermission('', 'themslides')")
	public String addOrEdit(ModelMap model) {
		TintucDTO tintucDTO = new TintucDTO();
		model.addAttribute("TINTUCDTO", tintucDTO);
		model.addAttribute("ACTION", "/news/saveOrUpdate");
		return "/admin/register-tintuc";
	}
	
	@RequestMapping("/list")
	public String list(ModelMap model, HttpSession session,HttpServletRequest request) {
		model.addAttribute("LIST_TINTUC", tinTucService.findAll());
		request.getSession().setAttribute("tintuclist", null);
		return "redirect:/news/page/1";

	}

	@GetMapping("/page/{pageNumber}")
//	@PreAuthorize("hasPermission('', 'danhsachslides')")
	public String showEmployeePage(Model mode, HttpServletRequest request, @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("tintuclist");
		int pagesize = 5;
		List<TinTuc> list = tinTucService.findAll();
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
		request.getSession().setAttribute("tintuclist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/news/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("LIST_TINTUC", pages);

		return "/admin/view-tintuc";
	}

	@PostMapping("/saveOrUpdate")
	public String save(HttpServletRequest request, ModelMap model, @Valid @ModelAttribute("TINTUCDTO") TintucDTO dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/admin/register-tintuc";
		} else {
			Optional<TinTuc> optionalProduct = tinTucService.findById(dto.getId());
			TinTuc tinTuc = null;
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

			tinTuc = new TinTuc(dto.getId(), dto.getTieude(), dto.getMieuta(), dto.getNoidungs(), image,
					dto.getNgayviet());
			tinTucService.save(tinTuc);
			request.getSession().setAttribute("tintuclist", null);
			return "/admin/register-tintuc";
		}
	}

	@RequestMapping("/edit/{id}")
//	@PreAuthorize("hasPermission('', 'suaslides')")
	public String edit(ModelMap model, HttpServletRequest request, @PathVariable(name = "id") Integer id,
			@Valid @ModelAttribute("TINTUCDTO") TintucDTO dtoo, BindingResult bindingResult) {
		Optional<TinTuc> slOptional = tinTucService.findById(id);
		TintucDTO dto = null;
		if (slOptional.isPresent()) {
			TinTuc pt = slOptional.get();
			File file = new File("uploads/" + pt.getImg());
			FileInputStream input;
			try {
				input = new FileInputStream(file);
				MultipartFile mutiPhoto = new MockMultipartFile("file", file.getName(), "text/plain",
						IOUtils.toByteArray(input));

				dto = new TintucDTO(pt.getId(), mutiPhoto, pt.getTieude(),
						pt.getMieuta(), pt.getNoidungs(),  pt.getNgayviet());

			} catch (FileNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			model.addAttribute("TINTUCDTO", dto);
			request.getSession().setAttribute("tintuclist", null);
		} else {
			model.addAttribute("TINTUCDTO", new TintucDTO());
		}
		model.addAttribute("ACTION", "/news/saveOrUpdate");
		return "/admin/register-tintuc";
	}

	@RequestMapping("/delete/{id}")
//	@PreAuthorize("hasPermission('', 'xoaslides')")
	public String deleteProduct(HttpServletRequest request, @PathVariable(name = "id") int id) {
		tinTucService.deleteById(id);
		;
		request.getSession().setAttribute("tintuclist", null);
		return "redirect:/news/page/{id}";
	}

}
