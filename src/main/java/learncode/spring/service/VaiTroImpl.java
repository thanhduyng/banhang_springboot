package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learncode.spring.model.ChucNang;
import learncode.spring.model.VaiTro;
import learncode.spring.repository.ChucNangRepository;
import learncode.spring.repository.VaiTroRepository;

@Service
public class VaiTroImpl implements VaiTroService{
	

	@Autowired
	VaiTroRepository vaiTroRepository;

	@Autowired
	ChucNangRepository chucNangRepository;
	
	@Autowired
	ChucNangService chucNangService;
	
	@Override
	public List<ChucNang> finAllChucNang(){
		return this.chucNangService.findAllChucNang();
	}
	
	@Override
	public void insertVaitro(VaiTro vt) {
		this.vaiTroRepository.insertVaitro(vt.getId(), vt.getMavaitro(), vt.getTenvaitro(), vt.getNguoitao(), vt.getCreateday(), vt.getNguoiupdate(), vt.getUpdateday(), 0);
		if (vt.getChucnang() != null) {
			for (ChucNang cn1 : vt.getChucnang()) {
				this.vaiTroRepository.insertVaitroVaChucnang(vt.getId(), cn1.getId());
			}
		}
	}

	@Override
	public List<VaiTro> listVaiTro() {
		return vaiTroRepository.listVaiTro();
	}

	@Override
	public void updateVaitro(VaiTro vt) {
		this.vaiTroRepository.updateVaitro(vt.getMavaitro(), vt.getTenvaitro(), vt.getNguoiupdate(), vt.getUpdateday(), vt.getIsdelete(), vt.getId());
		this.vaiTroRepository.deleteVaitroVaChucnang(vt.getId());
		if (vt.getChucnang() != null) {
			for (ChucNang cn : vt.getChucnang()) {
				this.vaiTroRepository.insertVaitroVaChucnang(vt.getId(), cn.getId());
			}
		}
	}

	@Override
	public Optional<VaiTro> findByVaitroId(Long id) {
		return vaiTroRepository.findByVaitroId(id);
	}

	@Override
	public List<VaiTro> findByMavaitro(String mavaitro) {
		return vaiTroRepository.findByMavaitro(mavaitro);
	}

	@Override
	public List<VaiTro> findByTenvaitro(String tenvaitro) {
		return vaiTroRepository.findByTenvaitro(tenvaitro);
	}

	@Override
	public List<Long> findChucnangVaitro(Long idvaitro) {
		return vaiTroRepository.findChucnangVaitro(idvaitro);
	}

	@Override
	public void deleteById(Long id) {
		vaiTroRepository.deleteById(id);
		
	}
	
	
}
