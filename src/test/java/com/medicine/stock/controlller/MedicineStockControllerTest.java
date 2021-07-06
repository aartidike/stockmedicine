package com.medicine.stock.controlller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.medicine.stock.controller.MedicineStockController;
import com.medicine.stock.model.MedicineStock;
import com.medicine.stock.model.MedicineStockTest;
import com.medicine.stock.service.MedicineStockRepoServiceTest;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class MedicineStockControllerTest {
	
	@InjectMocks
	private MedicineStockController medicineStockController;
	
	@Mock
	private MedicineStock medicineStock;
	
	@Mock
	private List<MedicineStock> medicineStockList;
	
	@Mock
	private MedicineStockRepoServiceTest medicineStockService;
	
	@Test
	public void contextLoads() {
		assertNotNull(medicineStockController);
	}
	
	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		medicineStock = new MedicineStock();
		medicineStock.setMedicineId(1);
		medicineStock.setPharmacyName("Crocin");
		medicineStock.setChemicalComposition("disaccharide gentiobiose, dicarboxylic acid crocetin");
		medicineStock.setTargetAilment("General");
		medicineStock.setPharmacyName("Healthy Pharmacy");
		medicineStock.setDateOfExpiry(new java.util.Date(2022-9-12));
		medicineStock.setNumberOfTabletsInStock(200);
		
	}
 
	@Test
	public void getMedicineStockInformationTest() throws Exception {
		
		ResponseEntity<List<MedicineStock>> medicineStockInformation = medicineStockController.getMedicineStock();
		HttpStatus statusCode = medicineStockInformation.getStatusCode();
		assertNotNull(statusCode);
		assertEquals(HttpStatus.OK, statusCode);
	}
	 
	@Test
	public void getMedicineByTreatingAilmentTest() {
		
		ResponseEntity<List<String>> medicineByTreatingAilment = medicineStockController.getMedicineByAilment("Orthopaedics");
		HttpStatus statusCode = medicineByTreatingAilment.getStatusCode();
		assertNotNull(statusCode);
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	@Test
	public void updateMedicineStockTest() throws Exception {
		
		this.mockMvc.perform(
						put("/update-stock/Crocin/1000")
					)
					.andExpect(status().isOk());
	}
	
	@Test
	public void invalidUpdateMedicineStockTest() throws Exception {
		
		this.mockMvc.perform(
				put("/update-stock/Crosin/1000")
				)
		.andExpect(status().isBadRequest());
	}
}
