package com.sermaluc.service.model;

import com.sermaluc.service.bean.NokDto;
import com.sermaluc.service.enums.CampoFileRecord;
import com.sermaluc.service.enums.ErrorSeverity;
import com.sermaluc.service.enums.EstadoFileRecord;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedNativeQuery(name = "MFileRecord.findFileStatusNokById",
query = "select fr.codigo_error codigo, count(fr.id) cantidad from file_record fr"
		+ " where fr.MFile_id=?1 and estado = 'ERROR'"
		+ " GROUP BY fr.codigo_error",
resultSetMapping = "Mapping.NokDto")
@SqlResultSetMapping(name = "Mapping.NokDto",
 classes = @ConstructorResult(targetClass = NokDto.class,
		 						columns = {@ColumnResult(name = "codigo"),
		 								   @ColumnResult(name = "cantidad")}))
@Entity
@Table(name="file_record")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MFileRecord {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Integer line;
	
	@Enumerated(EnumType.STRING)
	private CampoFileRecord campo;
	
	@Enumerated(EnumType.STRING) 
	private EstadoFileRecord estado;
	
	private Integer codigoError;
	
	@Column(length = 1)
	private ErrorSeverity gravedadError;
	
	private String mensajeError;
	
	@ManyToOne
    @JoinColumn(name="file_id")
    private MFile file;

}
