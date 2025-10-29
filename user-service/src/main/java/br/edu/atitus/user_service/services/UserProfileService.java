package br.edu.atitus.user_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.atitus.user_service.dtos.UserAddressDTO;
import br.edu.atitus.user_service.dtos.UserDTO;
import br.edu.atitus.user_service.dtos.UserDetailsDTO;
import br.edu.atitus.user_service.entities.UserAddressEntity;
import br.edu.atitus.user_service.entities.UserProfileEntity;
import br.edu.atitus.user_service.entities.UserProfileGenreEntity;
import br.edu.atitus.user_service.repositories.UserGenreRepository;
import br.edu.atitus.user_service.repositories.UserProfileRepository;

@Service
public class UserProfileService {

	private final UserProfileRepository userProfileRepository;
	private final UserGenreRepository userGenreRepository;

	public UserProfileService(UserProfileRepository userProfileRepository, UserGenreRepository userGenreRepository) {
		super();
		this.userProfileRepository = userProfileRepository;
		this.userGenreRepository = userGenreRepository;
	}
	
	// Informaçoes Gerais do Usuário

	@Transactional
	public UserProfileEntity createProfile(UserDTO dto) throws Exception {

		// TODO melhorar verificações
		if (dto.cpf() == null || dto.cpf().isEmpty()) {
			throw new Exception("CPF não pode ser nulo");
		}
		
		if (dto.phoneNumber() == null || dto.phoneNumber().isEmpty()) {
			throw new Exception("Número de Celular não pode ser nulo");
		}
		
		if (dto.dateOfBirth() == null) {
			throw new Exception("Data de nascimento não pode ser nulo");
		}

		UserProfileEntity profile = new UserProfileEntity();

		profile.setId(dto.id());
		
		profile.setCpf(dto.cpf());
		profile.setPhoneNumber(dto.phoneNumber());
		profile.setDateOfBirth(dto.dateOfBirth());
		
		UserProfileEntity savedProfile = userProfileRepository.save(profile);

		return savedProfile;
	}
	
	@Transactional
	public UserDTO alterInfo (UUID id, UserDTO dto) throws Exception {
		UserProfileEntity profile = userProfileRepository.findById(id)
				.orElseThrow(() -> new Exception("Usuário não encontrado no Banco de Dados"));
		
		if (dto.phoneNumber() == null || dto.phoneNumber().isEmpty()) {
			throw new Exception("Número de Celular não pode ser nulo");
		}
		
		if (dto.dateOfBirth() == null) {
			throw new Exception("Data de nascimento não pode ser nulo");
		}
		
		profile.setCpf(dto.cpf());
		profile.setDateOfBirth(dto.dateOfBirth());
		
		UserProfileEntity savedProfile = userProfileRepository.save(profile);
		
		return convertToInfoDTO(savedProfile);
		
	}
	
	@Transactional
	public UserDTO getInfoById (UUID id) throws Exception {
		 
		UserProfileEntity profile = userProfileRepository.findById(id)
				.orElseThrow(() -> new Exception ("Usuário não encontrado"));
		
		return convertToInfoDTO(profile);
	}
	
	//Endereço

	@Transactional
	public UserAddressEntity addAddress(UUID id, UserAddressDTO dto) throws Exception {
		UserProfileEntity profile = userProfileRepository.findById(id)
				.orElseThrow(() -> new Exception("Usuário não encontrado no Banco de Dados"));

		UserAddressEntity address = new UserAddressEntity();

		address.setCep(dto.cep());
		address.setCity(dto.city());
		address.setState(dto.state());
		address.setNeighborhood(dto.neighborhood());
		address.setStreet(dto.street());
		address.setStreetNumber(dto.streetNumber());
		address.setComplement(dto.complement());

		address.setUserProfileEntity(profile);
		profile.setUserAddressEntity(address);

		UserProfileEntity savedProfile = userProfileRepository.save(profile);

		return savedProfile.getUserAddressEntity();
	}

	@Transactional
	public UserAddressEntity alterAddress(UUID id, UserAddressDTO dto) throws Exception {
		UserProfileEntity profile = userProfileRepository.findById(id)
				.orElseThrow(() -> new Exception("Usuário não encontrado no Banco de Dados"));

		UserAddressEntity address = profile.getUserAddressEntity();

		if (address == null) {
			throw new Exception("Usuário não possui endereço");
		}

		address.setCep(dto.cep());
		address.setCity(dto.city());
		address.setState(dto.state());
		address.setNeighborhood(dto.neighborhood());
		address.setStreet(dto.street());
		address.setStreetNumber(dto.streetNumber());
		address.setComplement(dto.complement());

		userProfileRepository.save(profile);

		return address;
	}

	@Transactional
	public UserAddressEntity getAddressById(UUID id) throws Exception {
		UserProfileEntity profile = userProfileRepository.findById(id)
				.orElseThrow(() -> new Exception("Falha em buscar endereço"));

		UserAddressEntity address = profile.getUserAddressEntity();

		if (address == null) {
			throw new Exception("Endereço não informado");
		}
		return address;
	}
	
	//Detalhes
	
	@Transactional
	public UserDetailsDTO addDetails (UUID id, UserDetailsDTO dto) throws Exception {
		UserProfileEntity profile = userProfileRepository.findById(id)
				.orElseThrow(() -> new Exception ("Usuário não encontrado"));
		
		if (dto.userGenreId() != null) {
			UserProfileGenreEntity genre = userGenreRepository.findById(dto.userGenreId())
					.orElseThrow(() -> new Exception("Gênero não encontrado"));
			profile.setUserGenre(genre);
		}
		
		profile.setUserImageUrl(dto.userImageUrl());
		profile.setDescription(dto.description());
		
		UserProfileEntity savedProfile = userProfileRepository.save(profile);
		
		return convertoToDetailsDTO(savedProfile);
	}
	
	@Transactional
	public UserDetailsDTO updateDetails (UUID id, UserDetailsDTO dto) throws Exception {
		UserProfileEntity profile = userProfileRepository.findById(id)
				.orElseThrow(() -> new Exception ("Usuário não encontrado"));
		
		if (dto.userGenreId() != null) {
			UserProfileGenreEntity genre = userGenreRepository.findById(dto.userGenreId())
					.orElseThrow(() -> new Exception("Gênero não encontrado"));
			profile.setUserGenre(genre);
		}
		
		profile.setUserImageUrl(dto.userImageUrl());
		profile.setDescription(dto.description());
		
		UserProfileEntity savedProfile = userProfileRepository.save(profile);
		
		return convertoToDetailsDTO(savedProfile);
	}
	
	@Transactional
	public UserDetailsDTO getDetailsById (UUID id) throws Exception {
		UserProfileEntity profile = userProfileRepository.findById(id)
				.orElseThrow(() -> new Exception ("Usuário não encontrado"));
		
		return convertoToDetailsDTO(profile);
	}
	
	//Mappers
	
	private UserDTO convertToInfoDTO(UserProfileEntity profile) {
		return new UserDTO(
				profile.getId(),
				profile.getCpf(),
				profile.getPhoneNumber(),
				profile.getDateOfBirth());
	}
	
	private UserDetailsDTO convertoToDetailsDTO(UserProfileEntity profile) {
		return new UserDetailsDTO (
				profile.getUserImageUrl(),
				 profile.getUserGenre().getId(),
				 profile.getDescription());
	}
}
