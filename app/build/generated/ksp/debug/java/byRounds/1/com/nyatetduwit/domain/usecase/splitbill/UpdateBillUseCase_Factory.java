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
public final class UpdateBillUseCase_Factory implements Factory<UpdateBillUseCase> {
  private final Provider<SplitBillRepository> repositoryProvider;

  public UpdateBillUseCase_Factory(Provider<SplitBillRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateBillUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateBillUseCase_Factory create(Provider<SplitBillRepository> repositoryProvider) {
    return new UpdateBillUseCase_Factory(repositoryProvider);
  }

  public static UpdateBillUseCase newInstance(SplitBillRepository repository) {
    return new UpdateBillUseCase(repository);
  }
}
