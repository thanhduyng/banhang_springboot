package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import learncode.spring.model.ChucNang;
import learncode.spring.model.VaiTro;

public interface VaiTroService {

	void insertVaitro(VaiTro vt);

	List<VaiTro> listVaiTro();


	Optional<VaiTro> findByVaitroId(Long id);

	List<VaiTro> findByTenvaitro(String tenvaitro);

	List<VaiTro> findByMavaitro(String mavaitro);

	List<ChucNang> finAllChucNang();

	List<Long> findChucnangVaitro(Long idvaitro);

	void updateVaitro(VaiTro vt);

	void deleteById(Long id);

}
