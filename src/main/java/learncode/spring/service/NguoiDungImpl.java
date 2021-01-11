package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.comon.Xuly;
import learncode.spring.model.ChucNang;
import learncode.spring.model.Nguoidung;
import learncode.spring.model.VaiTro;
import learncode.spring.repository.ChucNangRepository;
import learncode.spring.repository.NguoiDungRepository;
import learncode.spring.repository.VaiTroRepository;

@Service
public class NguoiDungImpl implements NguoiDungService {

	@Autowired
	NguoiDungRepository nguoiDungRepository;

	@Autowired
	ChucNangRepository chucNangRepository;

	@Autowired
	ChucNangService chucNangService;

	@Autowired
	VaiTroRepository vaiTroRepository;

	@Override
	public List<ChucNang> finAllChucNang() {
		return this.chucNangService.findAllChucNang();
	}

	@Override
	public List<VaiTro> finAllVaiTro() {
		return this.vaiTroRepository.listVaiTro();
	}

	@Override
	public void insertNguoidung(Nguoidung nd) {
		this.nguoiDungRepository.insertNguoidung(nd.getId(), nd.getManguoidung(), nd.getTennguoidung(),
				Xuly.giaiMd5(nd.getPassword()), nd.getEmail(), nd.getGender(), nd.getPhone(), nd.getCreateday(),
				nd.getNguoitao(), nd.getUpdateday(), nd.getNguoiupdate(), nd.getIsdelete());

		if (nd.getVaitro() != null) {
			for (VaiTro vt : nd.getVaitro()) {
				this.nguoiDungRepository.insertNguoidungVaVaitro(nd.getId(), vt.getId());
			}
		}

	}

	@Override
	public void updateNguoidung(Nguoidung nd) {
		this.nguoiDungRepository.updateNguoidung(nd.getManguoidung(), nd.getTennguoidung(), Xuly.giaiMd5(nd.getPassword()),
				nd.getEmail(), nd.getGender(), nd.getPhone(), nd.getUpdateday(), nd.getNguoiupdate(), nd.getIsdelete(),
				nd.getId());
		this.nguoiDungRepository.deleteNguoidungVaVaitro(nd.getId());

		if (nd.getVaitro() != null) {
			for (VaiTro vt : nd.getVaitro()) {
				this.nguoiDungRepository.insertNguoidungVaVaitro(nd.getId(), vt.getId());
			}
		}

	}

	@Override
	public Nguoidung findByTen(String tennguoidung) {
		return nguoiDungRepository.findByTen(tennguoidung);
	}

	@Override
	public void deleteNguoidung(Nguoidung nd) {
		this.nguoiDungRepository.updateNguoidung(nd.getManguoidung(), nd.getTennguoidung(), nd.getPassword(),
				nd.getEmail(), nd.getGender(), nd.getPhone(), nd.getUpdateday(), nd.getNguoiupdate(), nd.getIsdelete(),
				nd.getId());
	}

	@Override
	public Optional<Nguoidung> findNguoidungById(Long id) {
		return nguoiDungRepository.findNguoidungById(id);
	}

	@Override
	public List<Nguoidung> getAllNguoiDung() {
		return nguoiDungRepository.getAllNguoiDung();
	}

	@Override
	public List<Nguoidung> findByTennguoidung(String tennguoidung) {
		return this.nguoiDungRepository.findByTennguoidung(tennguoidung);
	}

	@Override
	public Nguoidung findUrlChucNang(String tennguoidung) {
		Nguoidung nd = this.findByTen(tennguoidung);

		return nd;
	}



	@Override
	public List<Long> findByIdvaitro(Long idnguoidung) {
		return nguoiDungRepository.findByIdvaitro(idnguoidung);
	}

	@Override
	public Nguoidung findById1(Long id) {
		return nguoiDungRepository.findById1(id);
	}

//	@Override
//	public boolean checkLogin(String username, String password) {
//		Nguoidung nd = this.nguoiDungRepository.findByTen(username);
//		if (username != null || Xuly.checkMd5(password, nd.getPassword())) {
//			return true;
//		}
//		return false;
//	}

	@Override
	public Nguoidung findUrl(String tennguoidung) {
		return nguoiDungRepository.findUrl(tennguoidung, tennguoidung);
	}

	@Override
	public List<String> findUrlNd(String tennguoidung) {
		return nguoiDungRepository.findUrlNd(tennguoidung);
	}

	@Override
	public void deleteById(Long id) {
		nguoiDungRepository.findById(id);
	}


//	@Override
//	public boolean checkUrl(Nguoidung nd, String url) {
//		for (ChucNang1 chucnang : nd.getChucnang()) {
//			if (chucnang.getMaapi().equals(url)) {
//				return true;
//			}
//		}
//		return false;
//	}
}
