package com.nyatetduwit.domain.usecase.splitbill;

import com.nyatetduwit.domain.repository.SplitBillRepository;
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
public final class GetAllBillsUseCase_Factory implements Factory<GetAllBillsUseCase> {
  private final Provider<SplitBillRepository> repositoryProvider;

  public GetAllBillsUseCase_Factory(Provider<SplitBillRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAllBillsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAllBillsUseCase_Factory create(
      Provider<SplitBillRepository> repositoryProvider) {
    return new GetAllBillsUseCase_Factory(repositoryProvider);
  }

  public static GetAllBillsUseCase newInstance(SplitBillRepository repository) {
    return new GetAllBillsUseCase(repository);
  }
}
