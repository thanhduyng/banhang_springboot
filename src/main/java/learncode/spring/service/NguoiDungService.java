package learncode.spring.service;

import java.util.List;
import java.util.Optional;

import learncode.spring.model.ChucNang;
import learncode.spring.model.Nguoidung;
import learncode.spring.model.VaiTro;

public interface NguoiDungService {


	List<VaiTro> finAllVaiTro();

	List<ChucNang> finAllChucNang();

	List<Nguoidung> getAllNguoiDung();

	void insertNguoidung(Nguoidung nd);

	//boolean checkLogin(String username, String password);

	List<Nguoidung> findByTennguoidung(String tennguoidung);

	Nguoidung findUrlChucNang(String tennguoidung);

//	boolean checkUrl(Nguoidung nd, String url);

	void updateNguoidung(Nguoidung nd);

	Optional<Nguoidung> findNguoidungById(Long id);

	void deleteNguoidung(Nguoidung nd);

	Nguoidung findByTen(String tennguoidung);


	List<Long> findByIdvaitro(Long idnguoidung);

	Nguoidung findById1(Long id);

	Nguoidung findUrl(String tennguoidung);

	List<String> findUrlNd(String tennguoidung);

	void deleteById(Long id);

}
