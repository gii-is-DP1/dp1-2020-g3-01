package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Entity
//@Getter
//@Setter
@Table(name = "positions")
public class Position extends BaseEntity {

	@ManyToOne()
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Pilot pilot;

	@Column(name = "pos")
	@Range(min = 1, max = 20)
	@NotNull
	private Integer pos;

	@Column(name = "point")
	private Integer point;

	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getPoint() {

		Integer res = 0;
		switch (pos) {
		case 1:
			//setPoint(25);
			res = 25;
			break;
		case 2:
			//setPoint(20);
			res = 20;
			break;
		case 3:
			//setPoint(16);
			res = 16;
			break;
		case 4:
			//setPoint(13);
			res = 13;
			break;
		case 5:
			//setPoint(11);
			res = 11;
			break;
		case 6:
			//setPoint(10);
			res = 10;
			break;
		case 7:
			//setPoint(9);
			res = 9;
			break;
		case 8:
			//setPoint(8);
			res = 8;
			break;
		case 9:
			//setPoint(7);
			res = 7;
			break;
		case 10:
			//setPoint(6);
			res = 6;
			break;
		case 11:
			//setPoint(5);
			res = 5;
			break;
		case 12:
			//setPoint(4);
			res = 4;
			break;
		case 13:
			//setPoint(3);
			res = 3;
			break;
		case 14:
			//setPoint(2);
			res = 2;
			break;
		case 15:
			//setPoint(1);
			res = 1;
			break;
		case 16:
			//setPoint(0);
//			res = 0;
			break;
		case 17:
			//setPoint(0);
			res = 0;
			break;
		case 18:
			//setPoint(0);
			res = 0;
			break;
		case 19:
			//setPoint(0);
			res = 0;
			break;
		case 20:
			//setPoint(0);
			res = 0;
			break;

		}
		
		return res;

	}

}
