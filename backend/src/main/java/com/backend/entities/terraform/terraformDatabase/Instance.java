package com.backend.entities.terraform.terraformDatabase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.backend.entities.terraform.interfaces.IResource;

@Entity
public class Instance implements IResource{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private Integer vCpu;
	private Integer storage;
	private double memory;
	private double cost;
	private Integer imagen_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getImagen_id() {
		return imagen_id;
	}

	public void setImagen_id(Integer imagen_id) {
		this.imagen_id = imagen_id;
	}
	
	public String outputResource() {
		return "\t\tinstance_type = " + name + "\n";
	}

	public Integer getvCpu() {
		return vCpu;
	}

	public void setvCpu(Integer vCpu) {
		this.vCpu = vCpu;
	}

	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}


	

}
