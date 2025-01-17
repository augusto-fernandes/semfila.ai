package ai.semfila.api.controller;

import ai.semfila.api.DTO.company.CompanyRequest;
import ai.semfila.api.DTO.company.CompanyResponse;
import ai.semfila.api.DTO.company.CompanyUpdateRequest;
import ai.semfila.api.model.Company;
import ai.semfila.api.model.Product;
import ai.semfila.api.service.CompanyService;
import ai.semfila.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/v1/company")
@AllArgsConstructor
public class CompanyController {

    private CompanyService service;

    @GetMapping("/")
    public ResponseEntity<Page<CompanyResponse>> index(@PageableDefault() Pageable pageable){
        var allCompany = this.service.findAll(pageable).map(CompanyResponse::new);
        return ResponseEntity.ok().body(allCompany);
    }

    @PostMapping("/create")
    public ResponseEntity<CompanyResponse> save(@RequestBody @Valid CompanyRequest companyRequest){
        var company = this.service.save(new Company(companyRequest));
        return ResponseEntity.ok(new CompanyResponse(company));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> findById(@PathVariable UUID id) {
        var company = this.service.findById(id);

        return methodResponse(company);
    }

    @GetMapping("/cnpj/{cnpj}")
    public  ResponseEntity<CompanyResponse> findByCnpj(@PathVariable String cnpj){
        var company = this.service.findByCnpj(cnpj);
        return methodResponse(company);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<CompanyResponse> update(@PathVariable UUID id, @RequestBody CompanyUpdateRequest companyUpdateRequest){
        var company = this.service.update(id, companyUpdateRequest);
        return methodResponse(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id ){
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
    private static ResponseEntity<CompanyResponse> methodResponse(Optional<Company> company) {
        if (company.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(new CompanyResponse(company.get()));
    }

}
