package guru.springframework.sfgpetclinic.springjpadata;

import guru.springframework.sfgpetclinic.model.Person;
import guru.springframework.sfgpetclinic.model.PersonsList;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private RestTemplate restTemplate;

    private final String api_url;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String api_url) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }

    @Override
    public List<Person> getVets() {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(api_url);
        PersonsList personsList = restTemplate.getForObject(uriBuilder.toUriString(), PersonsList.class);
        return personsList.getPersons();
    }
}