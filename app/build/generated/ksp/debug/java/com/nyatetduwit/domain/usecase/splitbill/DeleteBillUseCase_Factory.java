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
public final class DeleteBillUseCase_Factory implements Factory<DeleteBillUseCase> {
  private final Provider<SplitBillRepository> repositoryProvider;

  public DeleteBillUseCase_Factory(Provider<SplitBillRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteBillUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteBillUseCase_Factory create(Provider<SplitBillRepository> repositoryProvider) {
    return new DeleteBillUseCase_Factory(repositoryProvider);
  }

  public static DeleteBillUseCase newInstance(SplitBillRepository repository) {
    return new DeleteBillUseCase(repository);
  }
}
