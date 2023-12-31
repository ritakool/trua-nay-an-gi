package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LienHe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String emailLienHe;

	private String noiDungLienHe;

	private String noiDungTraLoi;

	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone="GMT+7")
	private Date ngayLienHe;

	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone="GMT+7")
	private Date ngayTraLoi;

	private String trangThai;
	
	@ManyToOne
	@JoinColumn(name = "ma_nguoi_tra_loi")
	private NguoiDung nguoiTraLoi;

	public NguoiDung getNguoiTraLoi() {
		return nguoiTraLoi;
	}

	public void setNguoiTraLoi(NguoiDung nguoiTraLoi) {
		this.nguoiTraLoi = nguoiTraLoi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailLienHe() {
		return emailLienHe;
	}

	public void setEmailLienHe(String emailLienHe) {
		this.emailLienHe = emailLienHe;
	}

	public String getNoiDungLienHe() {
		return noiDungLienHe;
	}

	public void setNoiDungLienHe(String noiDungLienHe) {
		this.noiDungLienHe = noiDungLienHe;
	}

	public String getNoiDungTraLoi() {
		return noiDungTraLoi;
	}

	public void setNoiDungTraLoi(String noiDungTraLoi) {
		this.noiDungTraLoi = noiDungTraLoi;
	}

	public Date getNgayLienHe() {
		return ngayLienHe;
	}

	public void setNgayLienHe(Date ngayLienHe) {
		this.ngayLienHe = ngayLienHe;
	}

	public Date getNgayTraLoi() {
		return ngayTraLoi;
	}

	public void setNgayTraLoi(Date ngayTraLoi) {
		this.ngayTraLoi = ngayTraLoi;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
}
