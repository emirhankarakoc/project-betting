package com.betting.karakoc.service.runtimeServices;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.models.dtos.BetRoundEntityDTO;
import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.enums.UserRole;
import com.betting.karakoc.models.real.GameEntity;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.CreateManagerRequest;
import com.betting.karakoc.models.requests.CreateUserRequest;
import com.betting.karakoc.repository.BetRoundEntityRepository;
import com.betting.karakoc.repository.GameRepository;
import com.betting.karakoc.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.betting.karakoc.models.real.BetRoundEntity.betroundsToDtos;
import static com.betting.karakoc.models.real.UserEntity.userToDto;

@Service
@AllArgsConstructor
public class RuntimeManager {
    private BetRoundEntityRepository betroundRepository;
    private GameRepository gameRepository;
    private UserEntityRepository userRepository;
    Random random;


    public List<BetRoundEntityDTO> getAllBetrounds(int creatorCode){
      return  betroundsToDtos(betroundRepository.findAllByCreatorCode(creatorCode));
    }

    public List<GameEntity> getAllGames(String betroundId,int creatorCode){

        return gameRepository.findAllByBetroundIdAndCreatorCode(betroundId,creatorCode);
    }



}
