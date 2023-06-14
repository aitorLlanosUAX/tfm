package com.executor.entities;

import java.util.Objects;

public class ExecutableFile {

	private String docker_image;
	private Integer process_id;

	public ExecutableFile() {
	}

	public ExecutableFile(String docker, Integer id) {
		this.docker_image = docker;
		this.process_id = id;
	}

	public String getDocker_image() {
		return docker_image;
	}

	public void setDocker_image(String docker_image) {
		this.docker_image = docker_image;
	}



	public Integer getProcess_id() {
		return process_id;
	}

	public void setProcess_id(Integer process_id) {
		this.process_id = process_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(docker_image, process_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExecutableFile other = (ExecutableFile) obj;
		return Objects.equals(docker_image, other.docker_image) && Objects.equals(process_id, other.process_id);
	}

	@Override
	public String toString() {
		return "ExecutableFile [docker_image=" + docker_image + ", process_id=" + process_id + "]";
	}
	
	
}
