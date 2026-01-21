package com.truongsonkmhd.unetistudy.mapper.user;

import com.truongsonkmhd.unetistudy.dto.user_dto.UserRequest;
import com.truongsonkmhd.unetistudy.model.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-17T22:23:59+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class UserRequestMapperImpl implements UserRequestMapper {

    @Override
    public User toEntity(UserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.fullName( dto.getFullName() );
        user.gender( dto.getGender() );
        user.birthday( dto.getBirthday() );
        user.email( dto.getEmail() );
        user.phone( dto.getPhone() );
        user.password( dto.getPassword() );
        user.contactAddress( dto.getContactAddress() );
        user.currentResidence( dto.getCurrentResidence() );

        return user.build();
    }

    @Override
    public UserRequest toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserRequest userRequest = new UserRequest();

        return userRequest;
    }

    @Override
    public List<User> toEntity(List<UserRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserRequest userRequest : dtoList ) {
            list.add( toEntity( userRequest ) );
        }

        return list;
    }

    @Override
    public List<UserRequest> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserRequest> list = new ArrayList<UserRequest>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public Set<User> toEntity(Set<UserRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<User> set = new LinkedHashSet<User>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( UserRequest userRequest : dtoSet ) {
            set.add( toEntity( userRequest ) );
        }

        return set;
    }

    @Override
    public Set<UserRequest> toDto(Set<User> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<UserRequest> set = new LinkedHashSet<UserRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( User user : entitySet ) {
            set.add( toDto( user ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(User entity, UserRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getFullName() != null ) {
            entity.setFullName( dto.getFullName() );
        }
        if ( dto.getGender() != null ) {
            entity.setGender( dto.getGender() );
        }
        if ( dto.getBirthday() != null ) {
            entity.setBirthday( dto.getBirthday() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getContactAddress() != null ) {
            entity.setContactAddress( dto.getContactAddress() );
        }
        if ( dto.getCurrentResidence() != null ) {
            entity.setCurrentResidence( dto.getCurrentResidence() );
        }
    }
}
