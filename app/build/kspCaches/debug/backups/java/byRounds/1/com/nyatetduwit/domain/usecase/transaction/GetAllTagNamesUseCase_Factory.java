package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.TransactionTagRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class GetAllTagNamesUseCase_Factory implements Factory<GetAllTagNamesUseCase> {
  private final Provider<TransactionTagRepository> repositoryProvider;

  public GetAllTagNamesUseCase_Factory(Provider<TransactionTagRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAllTagNamesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAllTagNamesUseCase_Factory create(
      Provider<TransactionTagRepository> repositoryProvider) {
    return new GetAllTagNamesUseCase_Factory(repositoryProvider);
  }

  public static GetAllTagNamesUseCase newInstance(TransactionTagRepository repository) {
    return new GetAllTagNamesUseCase(repository);
  }
}
