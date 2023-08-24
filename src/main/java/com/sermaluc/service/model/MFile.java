package com.sermaluc.service.model;

import java.util.List;

import com.sermaluc.service.bean.EstadoArchivoDto;
import com.sermaluc.service.enums.EstadoFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedNativeQuery(name = "MFile.findFileStatusById",
					query = "select f.name, f.status, count(fr.*) countOk from File f"
							+ " left join file_record fr on f.id=fr.mfile_id and fr.estado = 'OK'"
							+ " where f.ID=?1"
							+ " GROUP BY f.name, f.status",
					resultSetMapping = "Mapping.EstadoArchivoDto")
@SqlResultSetMapping(name = "Mapping.EstadoArchivoDto",
					 classes = @ConstructorResult(targetClass = EstadoArchivoDto.class,
							 						columns = {@ColumnResult(name = "name"),
							 								   @ColumnResult(name = "status"),
							 								   @ColumnResult(name = "countOk")}))
@Entity
@Table(name="file", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MFile {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING) 
	private EstadoFile status;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mfile_id")
    private List<MFileRecord> lFileRecord;
	
}
