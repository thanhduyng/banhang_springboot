package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import learncode.spring.model.ChucNang;

public interface ChucNangService {

	int insertChucNang(ChucNang cn);

	int updateChucNang(ChucNang cn);

	int deleteChucNang(List<ChucNang> cn);

	List<ChucNang> findAllChucNang();

	List<ChucNang> findByTenchucnang(String tenchucnang);

	List<ChucNang> getAllChucNang();

	List<ChucNang> getAllChucNangParent();

	Optional<ChucNang> findByChucNangEditId(Long id);

	long count(Long id);

	List<ChucNang> findByMachucnang(String machucnang);

	Optional<ChucNang> findById(Long id);

	List<ChucNang> findChucnangByTennguoidung(String tennguoidung);

	List<String> maapi();
	
	void deleteById(Long id);

}
