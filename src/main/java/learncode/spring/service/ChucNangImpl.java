package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learncode.spring.model.ChucNang;
import learncode.spring.repository.ChucNangRepository;

@Service
@Transactional
public class ChucNangImpl implements ChucNangService {

	@Autowired
	ChucNangRepository chucNangRepository;

	@Override
	public List<ChucNang> findAllChucNang() {
		return chucNangRepository.findAllChucNang();
	}

	@Override
	public List<ChucNang> getAllChucNang() {
		return chucNangRepository.getAllChucNang();
	}

	@Override
	public List<ChucNang> getAllChucNangParent() {
		return chucNangRepository.getAllChucNangParent();
	}

	@Override
	public Optional<ChucNang> findByChucNangEditId(Long id) {
		return this.chucNangRepository.findByChucNangEditId(id);
	}

	@Override
	public int deleteChucNang(List<ChucNang> cn) {
		for (ChucNang ChucNang : cn) {
			this.chucNangRepository.deleteChucNang(ChucNang.getId());
		}
		return chucNangRepository.deleteChucNang(cn.get(0).getId());
	}

	@Override
	public Optional<ChucNang> findById(Long id) {
		return chucNangRepository.findById(id);
	}

	@Override
	public List<ChucNang> findByTenchucnang(String tenchucnang) {
		return chucNangRepository.findByTenchucnang(tenchucnang);
	}

	@Override
	public int insertChucNang(ChucNang cn) {
		return chucNangRepository.insertChucNang(cn.getId(), cn.getMachucnang(), cn.getTenchucnang(), cn.getMaapi(),
				cn.getCreateday(), cn.getNguoitao(), cn.getUpdateday(), cn.getNguoiupdate(), cn.getParentid(), 0);
	}

	@Override
	public int updateChucNang(ChucNang cn) {
		return chucNangRepository.updateChucNang(cn.getMachucnang(), cn.getTenchucnang(), cn.getMaapi(),
				cn.getUpdateday(), cn.getNguoiupdate(), cn.getId());
	}

	@Override
	public long count(Long id) {
		return chucNangRepository.count(id);
	}

	@Override
	public List<ChucNang> findByMachucnang(String machucnang) {
		return chucNangRepository.findByMachucnang(machucnang);
	}

	@Override
	public List<ChucNang> findChucnangByTennguoidung(String tennguoidung) {
		return chucNangRepository.findChucnangByTennguoidung(tennguoidung, tennguoidung);
	}

	@Override
	public List<String> maapi() {
		return chucNangRepository.maapi();
	}

	@Override
	public void deleteById(Long id) {
		chucNangRepository.deleteById(id);

	}

}
